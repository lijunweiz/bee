package cn.unminded.bee.core.okhttp3;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import okhttp3.*;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 记录日志
 * @author lijunwei
 */
public class LogInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    private static final Charset DEFAULT_CHAR_SET = StandardCharsets.UTF_8;

    @Override
    public Response intercept(Chain chain) throws IOException {
        long start = System.currentTimeMillis();
        Request request = chain.request();
        this.logRequest(request);
        Response response = chain.proceed(request);
        long end = System.currentTimeMillis();
        this.logResponse(response, end - start);

        return response;
    }

    private void logRequest(Request request) throws IOException {
        Map<String, Object> headers = Maps.newHashMap();
        Set<String> names = request.headers().names();
        if (CollectionUtils.isNotEmpty(names)) {
            names.forEach(x -> headers.put(x, request.headers().get(x)));
        }
        RequestBody requestBody = request.body();
        boolean hasRequestBody = Objects.nonNull(requestBody);
        String body = null;
        if (hasRequestBody) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = DEFAULT_CHAR_SET;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(DEFAULT_CHAR_SET);
            }

            body = buffer.readString(charset);
        }

        logger.info("request method: {}, url: {}, headers: {}, body:{}", request.method(), request.url(), JSON.toJSONString(headers), body);
    }

    private void logResponse(Response response, long cost) throws IOException {
        Map<String, Object> headers = Maps.newHashMap();
        Set<String> names = response.headers().names();
        if (CollectionUtils.isNotEmpty(names)) {
            names.forEach(x -> headers.put(x, response.headers().get(x)));
        }

        String body = null;
        if (HttpHeaders.hasBody(response)) {
            ResponseBody responseBody = response.body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.getBuffer();
            Charset charset = DEFAULT_CHAR_SET;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(DEFAULT_CHAR_SET);
            }

            body = buffer.clone().readString(charset);
        }

        logger.info("response code: {}, message: {}, url: {}, headers: {}, body: {}, 耗时: {}ms", response.code(),
                response.message(), response.request().url(), JSON.toJSONString(headers), body, cost);
    }

}
