package cn.unminded.bee.common.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lijunwei
 */
@Configuration
public class WebConfig {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final LocalDateTimeSerializer SERIALIZER = new LocalDateTimeSerializer(DATE_TIME_FORMATTER);
    private static final LocalDateTimeDeserializer DESERIALIZER = new LocalDateTimeDeserializer(DATE_TIME_FORMATTER);

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeLocalDateTimeFormat() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, SERIALIZER);
            jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, DESERIALIZER);
        };
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")// 允许访问的源，可以是具体的 URL 或 "*" 表示任意源
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的 HTTP 方法
                        .allowedHeaders("*") // 允许的 HTTP 头
                        .allowCredentials(true); // 是否允许发送 cookie
            }
        };
    }
}
