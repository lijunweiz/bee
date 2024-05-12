package cn.unminded.bee.common.filter;

import cn.unminded.bee.common.util.IPHelper;
import cn.unminded.bee.common.util.LogHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author lijunwei
 */
@Component
public class DefendersFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(DefendersFilter.class);

    @Value("${bee.security.ip.whitelist}")
    private List<String> ipWhitelist;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        if (uri.startsWith("/manage") || uri.startsWith("/favicon.ico") || this.isIpWhitelist(IPHelper.getRemoteIP(req))) {
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
        return CollectionUtils.isNotEmpty(ipWhitelist) && ipWhitelist.contains(ip);
    }

}