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
        String sb = null;
        try {
            sb = new StringBuilder()
                    .append(IPHelper.getRemoteIP(request)).append(" ")
                    .append(request.getMethod()).append(" ")
                    .append(Objects.nonNull(request.getContentType()) ? request.getContentType() : StringUtils.EMPTY).append(Objects.nonNull(request.getContentType()) ? " " : StringUtils.EMPTY)
                    .append(request.getRequestURI()).append(Objects.nonNull(request.getQueryString()) ? "?" : StringUtils.EMPTY)
                    .append(Objects.nonNull(request.getQueryString()) ? request.getQueryString() : StringUtils.EMPTY)
                    .toString();
            sb = URLDecoder.decode(sb, StandardCharsets.UTF_8.displayName());
        } catch (UnsupportedEncodingException e) {
            //ignore
        }

        return sb;
    }

    public static String logBody(HttpServletRequest request) {
        if (Objects.isNull(request.getContentType()) || !request.getContentType().contains("json")) {
            return StringUtils.EMPTY;
        }

        try {
            return IOUtils.readString(request.getInputStream());
        } catch (IOException e) {
            // ignore
        }

        return StringUtils.EMPTY;
    }

}
