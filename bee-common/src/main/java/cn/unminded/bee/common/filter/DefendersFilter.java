package cn.unminded.bee.common.filter;

import cn.unminded.bee.common.util.LogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lijunwei
 */
@Component
public class DefendersFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(DefendersFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        if (uri.startsWith("/manage")) {
            chain.doFilter(request, response);
        } else {
            String url = LogHelper.appendUrl(req);
            String body = LogHelper.logBody(req);
            logger.error("异常请求: {}, {}", url, body);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Error");
        }
    }

}