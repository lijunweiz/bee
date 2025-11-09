package cn.unminded.bee.common.util;

import cn.unminded.rtool.util.IOUtils;
import cn.unminded.rtool.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class LogHelper {

    private LogHelper() {
        throw new UnsupportedOperationException();
    }

    public static String appendUrl(HttpServletRequest request) {
        String sbLog = null;
        try {
            StringBuilder sb = new StringBuilder()
                    .append(IPHelper.getRemoteIP(request)).append(",")
                    .append(request.getMethod()).append(",")
                    .append("Content-Type: ").append(request.getContentType()).append(",")
                    .append("X-Token: ").append(request.getHeader("X-Token")).append(",")
                    .append(URLDecoder.decode(request.getRequestURI(), StandardCharsets.UTF_8.displayName()));
            if (Objects.nonNull(request.getQueryString())) {
                sb.append("?").append(request.getQueryString());
            }

            sbLog = URLDecoder.decode(sb.toString(), StandardCharsets.UTF_8.displayName());
        } catch (UnsupportedEncodingException e) {
            //ignore
        }

        return sbLog;
    }

    public static String logBody(HttpServletRequest request) {
        try {
            return IOUtils.readString(request.getInputStream());
        } catch (IOException e) {
            // ignore
        }

        return StringUtils.EMPTY;
    }

    public static String logJsonBody(HttpServletRequest request) {
        if (Objects.isNull(request.getContentType()) || !request.getContentType().contains("json")) {
            throw new IllegalArgumentException("不能识别的数据格式: " + request.getContentType());
        }

        return logBody(request);
    }

}
