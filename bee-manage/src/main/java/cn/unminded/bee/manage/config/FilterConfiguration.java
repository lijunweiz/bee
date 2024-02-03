package cn.unminded.bee.manage.config;

import cn.unminded.bee.common.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.Collections;

/**
 * @author lijunwei
 */
@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean(CorsFilter corsFilter) {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter( corsFilter);
        registrationBean.setName("corsFilter");
        registrationBean.setUrlPatterns(Collections.singleton("/manage/*"));
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);

        return registrationBean;
    }

}
