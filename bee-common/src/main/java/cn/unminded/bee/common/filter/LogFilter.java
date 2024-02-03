package cn.unminded.bee.common.filter;

import cn.unminded.bee.common.util.LogHelper;
import cn.unminded.rtool.util.*;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

//@Component
public class LogFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        boolean noLogResource = requestURI.contains("/actuator");
        if (noLogResource) {
            chain.doFilter(request, response);
        } else {
            String log = LogHelper.appendUrl(httpServletRequest);
            if (requestURI.equals("/")
                    || requestURI.startsWith("/manage")) {
                // 只打印json body
                if (Objects.nonNull(httpServletRequest.getContentType()) && httpServletRequest.getContentType().contains("json")) {
                    RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
                    log += requestWrapper.body;
                    logger.info("beginTime: {}, request: {}", DateUtils.ymdHms3S(), log);
                    chain.doFilter(requestWrapper, response);
                } else {
                    logger.info("beginTime: {}, request: {}", DateUtils.ymdHms3S(), log);
                    chain.doFilter(request, response);
                }
            } else {
                request.setAttribute("trace", String.format("请求资源 [%s] 不存在", requestURI));
                request.getRequestDispatcher("/error").forward(request, response);
            }
        }
    }

    static class RequestWrapper extends HttpServletRequestWrapper {

        private final String body;

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request The request to wrap
         * @throws IllegalArgumentException if the request is null
         */
        public RequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            String base64 = request.getParameter("base64");
            String t = IOUtils.readString(request.getInputStream());
            if (Boolean.parseBoolean(base64) && StringUtils.isNotEmpty(t)) {
                Map<String, Object> map = JSONUtils.parseObject(t, new TypeReference<Map<String, Object>>() {});
                this.redefine(map);
                this.body = JSONUtils.toJSONString(map);
            } else {
                this.body = t;
            }
        }

        private void redefine(Map<String, Object> map) {
            if (CollectionUtils.isEmpty(map)) {
                return;
            }
            map.forEach((k, v) -> {
                if (v instanceof Map) {
                    Map<String, Object> vMap = JSONUtils.parseObject(JSONUtils.toJSONString(v), new TypeReference<Map<String, Object>>() {});
                    vMap.forEach((vk, vv) -> {
                        if (Objects.nonNull(vMap.get(vk))) {
                            vMap.put(vk, new String(Base64.getDecoder().decode(map.get(vk).toString())));
                        }
                    });
                    map.put(k, vMap);
                } else {
                    if (Objects.nonNull(map.get(k))) {
                        map.put(k, new String(Base64.getDecoder().decode(map.get(k).toString())));
                    }
                }
            });
        }


        @Override
        public ServletInputStream getInputStream() throws IOException {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener listener) {
                    // ignore
                }

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }
    }

}
