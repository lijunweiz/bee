package cn.unminded.bee.common.filter;

import cn.unminded.bee.common.util.IPHelper;
import cn.unminded.bee.common.util.LogHelper;
import cn.unminded.rtool.util.IOUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * @author lijunwei
 */
@Component
public class DefendersFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(DefendersFilter.class);

    @Value("${bee.security.ip.whitelist:null}")
    private List<String> ipWhitelist;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        if (Objects.equals(uri.trim(), "/")) {
            InputStream is = DefendersFilter.class.getClassLoader().getResourceAsStream("intro.html");
            resp.setHeader("Content-Type", "text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(IOUtils.readString(is));
            logger.warn("获取系统简介IP: {}", IPHelper.getRemoteIP(req));
        } else if (uri.startsWith("/manage")
                || uri.startsWith("/favicon.ico")
                || this.isIpWhitelist(IPHelper.getRemoteIP(req))) {
            chain.doFilter(request, response);
        } else {
            String url = LogHelper.appendUrl(req);
            String body = LogHelper.logBody(req);
            logger.error("异常请求: {}, {}", url, body);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Error");
        }
    }

    private boolean isIpWhitelist(String ip) {
        if (CollectionUtils.isEmpty(ipWhitelist)) {
            return false;
        }

        for (String ipWhite : ipWhitelist) {
            if (Objects.equals(ipWhite, ip)) {
                return true;
            }

            if (ipWhite.contains("*")) {
                String replace = ipWhite.replace("*", "");
                if (StringUtils.isEmpty(replace)) {
                    return true;
                }

                return ip.startsWith(replace);
            }
        }

        return false;
    }

}