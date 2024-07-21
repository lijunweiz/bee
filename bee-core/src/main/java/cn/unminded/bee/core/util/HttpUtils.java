package cn.unminded.bee.core.util;

import cn.unminded.bee.core.constant.BeeConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

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

    private HttpUtils() {
        throw new UnsupportedOperationException();
    }

    private static final OkHttpClient OK_HTTP_CLIENT;

    static {
        Properties properties = BeeUtils.readProperties();
        TimeUnit timeUnit = Objects.nonNull(properties.get("okhttp.timeUnit")) ? TimeUnit.valueOf(properties.getProperty("okhttp.timeUnit").toUpperCase(Locale.ROOT)) : BeeConstant.DEFAULT_TIME_UNIT;
        long connectTimeout = Objects.nonNull(properties.get("okhttp.connectTimeout")) ? Long.parseLong(properties.get("okhttp.connectTimeout").toString()) : BeeConstant.DEFAULT_TIMEOUT;
        long readTimeout = Objects.nonNull(properties.get("okhttp.readTimeout")) ? Long.parseLong(properties.get("okhttp.readTimeout").toString()) : BeeConstant.DEFAULT_TIMEOUT;
        long writeTimeout = Objects.nonNull(properties.get("okhttp.writeTimeout")) ? Long.parseLong(properties.get("okhttp.writeTimeout").toString()) : BeeConstant.DEFAULT_TIMEOUT;
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
                    System.err.println("未发现class: " + interceptor);
                    System.exit(-1);
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

    public static String post(String url, Map<String, String> param, Map<String, String> headers, Object body) throws IOException {
        return string(BeeConstant.POST, url, param, headers, body);
    }

    public static String string(String method, String url, Map<String, String> param, Map<String, String> headers, Object body) throws IOException {
        Response response = invoke(method, url, param, headers, body);
        return Objects.nonNull(response.body()) ? response.body().string() : null;
    }

    public static <T> T invoke(String method, String url, Map<String, String> param, Map<String, String> headers, Object body, TypeReference<T> typeReference) throws IOException {
        Response response = invoke(method, url, param, headers, body);
        return JSON.parseObject(Objects.nonNull(response.body()) ? response.body().string() : null, typeReference);
    }

    public static <T> T invoke(String method, String url, Map<String, String> param, Map<String, String> headers, Object body, Class<T> clazz) throws IOException {
        Response response = invoke(method, url, param, headers, body);
        return JSON.parseObject(Objects.nonNull(response.body()) ? response.body().string() : null, clazz);
    }

    private static Response invoke(String method, String url, Map<String, String> param, Map<String, String> headers, Object body) throws IOException {
        // 处理查询参数
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (Objects.isNull(httpUrl)) {
            throw new IllegalArgumentException("url: " + url + ", 格式异常");
        }
        if (Objects.nonNull(param) && !param.isEmpty()) {
            StringBuilder sb = new StringBuilder(url);
            if (Objects.isNull(httpUrl.query())) {
                sb.append("?");
            }
            if (StringUtils.length(httpUrl.query()) > 0) {
                sb.append("&");
            }
            param.forEach((k, v) -> sb.append(k).append("=").append(v).append("&"));
            url = sb.substring(0, sb.length() - 1);
        }
        // 处理请求头
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (Objects.nonNull(headers) && !headers.isEmpty()) {
            headers.forEach(requestBuilder::header);
        }

        // 执行调用
        if (Objects.equals(method, BeeConstant.GET)) {
            Request request = requestBuilder.get().build();
            return OK_HTTP_CLIENT.newCall(request).execute();
        } else {
            Objects.requireNonNull(body, "POST body 不能为null");
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(body));
            Request request = requestBuilder.post(requestBody).build();
            return OK_HTTP_CLIENT.newCall(request).execute();
        }
    }

}
