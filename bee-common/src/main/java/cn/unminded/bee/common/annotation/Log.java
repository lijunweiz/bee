package cn.unminded.bee.common.annotation;


import java.lang.annotation.*;

/**
 * @author lijunwei
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 打印日志的业务名
     * @return 日志业务名称
     */
    String value() default "";

    /**
     * 忽略请求日志
     * @return true 忽略,不打印请求日志 or false 不忽略,打印请求日志。默认false
     */
    boolean ignoreRequest() default false;

    /**
     * 忽略响应日志
     * @return true 忽略,不打印响应日志 or false 不忽略,打印响应日志。默认false
     */
    boolean ignoreResponse() default false;

}
