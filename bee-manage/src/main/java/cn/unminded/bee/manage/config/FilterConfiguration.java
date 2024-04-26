package cn.unminded.bee.manage.config;

import cn.unminded.bee.common.filter.CorsFilter;
import cn.unminded.bee.common.filter.DefendersFilter;
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
    public FilterRegistrationBean<DefendersFilter> defendersFilterRegistrationBean(DefendersFilter defendersFilter) {
        FilterRegistrationBean<DefendersFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(defendersFilter);
        registrationBean.setName("defendersFilter");
        registrationBean.setUrlPatterns(Collections.singleton("/*"));
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return registrationBean;
    }

//    @Bean
//    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean(CorsFilter corsFilter) {
//        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(corsFilter);
//        registrationBean.setName("corsFilter");
//        registrationBean.setUrlPatterns(Collections.singleton("/**"));
//        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//
//        return registrationBean;
//    }

}
