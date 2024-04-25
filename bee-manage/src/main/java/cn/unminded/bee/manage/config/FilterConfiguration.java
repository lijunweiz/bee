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

//    @Bean
//    public FilterRegistrationBean<CorsFilter> corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true); // 允许携带cookie
//        config.addAllowedOrigin("*"); // 允许任何域名访问
//        config.addAllowedHeader("*"); // 允许任何头
//        config.addAllowedMethod("*"); // 允许任何方法（GET、POST等）
//
//        source.registerCorsConfiguration("/**", config); // 对所有的路径都适用
//
//        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(Ordered.HIGHEST_PRECEDENCE); // 设置最高优先级，保证最先执行
//        return bean;
//    }

}
