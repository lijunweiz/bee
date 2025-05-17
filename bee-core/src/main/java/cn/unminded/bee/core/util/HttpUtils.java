package cn.unminded.bee.core.util;

import cn.unminded.bee.core.constant.BeeConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * okHttp3实现的http工具类
 * @author lijunwei
 */
public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private HttpUtils() {
        throw new UnsupportedOperationException();
    }

    private static final OkHttpClient OK_HTTP_CLIENT;

    static {
        Properties properties = BeeUtils.getBeeProperties();
        TimeUnit timeUnit = Objects.nonNull(properties.get("okhttp.timeUnit")) ?
                TimeUnit.valueOf(properties.getProperty("okhttp.timeUnit").toUpperCase(Locale.ROOT)) : BeeConstant.DEFAULT_TIME_UNIT;
        long connectTimeout = Objects.nonNull(properties.get("okhttp.connectTimeout")) ?
                Long.parseLong(properties.get("okhttp.connectTimeout").toString()) : BeeConstant.DEFAULT_TIMEOUT;
        long readTimeout = Objects.nonNull(properties.get("okhttp.readTimeout")) ?
                Long.parseLong(properties.get("okhttp.readTimeout").toString()) : BeeConstant.DEFAULT_TIMEOUT;
        long writeTimeout = Objects.nonNull(properties.get("okhttp.writeTimeout")) ?
                Long.parseLong(properties.get("okhttp.writeTimeout").toString()) : BeeConstant.DEFAULT_TIMEOUT;
        String interceptors = (String) properties.get("okhttp.interceptors");
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .sslSocketFactory(createInsecureSslSocketFactory(), new TrustAllCerts())
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .connectionPool(new ConnectionPool())
                .connectTimeout(connectTimeout, timeUnit)
                .readTimeout(readTimeout, timeUnit)
                .writeTimeout(writeTimeout, timeUnit)
                ;

        if (Objects.nonNull(interceptors)) {
            for (String interceptor : interceptors.split(",")) {
                try {
                    if (StringUtils.isNotBlank(interceptor)) {
                        builder.addInterceptor((Interceptor) BeeUtils.newInstance(interceptor));
                    }
                } catch (ClassNotFoundException e) {
                    logger.warn("未找到class: {}, 忽略实例化异常", interceptor);
                }
            }
        }
        OK_HTTP_CLIENT = builder.build();
    }

    @SuppressWarnings("all")
    private static SSLSocketFactory createInsecureSslSocketFactory() {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {}

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {}

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            return context.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("all")
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    @SuppressWarnings("all")
    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {}

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public static String get(String url) throws IOException {
        return get(url, Collections.emptyMap());
    }

    public static String get(String url, Map<String, String> param) throws IOException {
        return get(url, param, Collections.emptyMap());
    }

    public static String get(String url, Map<String, String> param, Map<String, String> headers) throws IOException {
        return string(BeeConstant.GET, url, param, headers, Object.class);
    }

    public static String post(String url, Object body) throws IOException {
        return post(url, Collections.emptyMap(), body);
    }

    public static String post(String url, Map<String, String> headers, Object body) throws IOException {
        return post(url, Collections.emptyMap(), headers, body);
    }

    public static String post(String url, Map<String, String> param, Map<String, String> headers,
                              Object body) throws IOException {
        return string(BeeConstant.POST, url, param, headers, body);
    }

    public static String string(String method, String url, Map<String, String> param, Map<String, String> headers,
                                Object body) throws IOException {
        Response response = invoke(method, url, param, headers, body);
        return Objects.nonNull(response.body()) ? response.body().string() : null;
    }

    public static <T> T invoke(String method, String url, Map<String, String> param, Map<String, String> headers,
                               Object body, TypeReference<T> typeReference) throws IOException {
        Response response = invoke(method, url, param, headers, body);
        return JSON.parseObject(Objects.nonNull(response.body()) ? response.body().string() : null, typeReference);
    }

    public static <T> T invoke(String method, String url, Map<String, String> param, Map<String, String> headers,
                               Object body, Class<T> clazz) throws IOException {
        Response response = invoke(method, url, param, headers, body);
        return JSON.parseObject(Objects.nonNull(response.body()) ? response.body().string() : null, clazz);
    }

    public static Response httpResponse(String method, String url, Map<String, String> param,
                                        Map<String, String> headers, Object body) throws IOException {
        return invoke(method, url, param, headers, body);
    }

    private static Response invoke(String method, String url, Map<String, String> param,
                                   Map<String, String> headers, Object body) throws IOException {
        // 处理查询参数
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (Objects.isNull(httpUrl)) {
            throw new IllegalArgumentException("url: " + url + ", 格式异常");
        }
        HttpUrl.Builder httpUrlBuilder = httpUrl.newBuilder();
        if (Objects.nonNull(param) && !param.isEmpty()) {
            param.forEach(httpUrlBuilder::addQueryParameter);
        }

        // 处理请求头
        Request.Builder requestBuilder = new Request.Builder().url(httpUrlBuilder.build());
        if (Objects.nonNull(headers) && !headers.isEmpty()) {
            headers.forEach(requestBuilder::header);
        }

        Request request = null;
        // 执行调用
        if (Objects.equals(method, BeeConstant.GET)) {
            request = requestBuilder.get().build();
        } else {
            Objects.requireNonNull(body, "body不能为null");
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(body));
            request = requestBuilder.post(requestBody).build();
        }

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            return response;
        }
    }

}
