package cn.unminded.bee.common.config;

import cn.unminded.bee.common.annotation.Log;
import cn.unminded.bee.common.util.LogHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 控制器日志日志记录组件
 * @author lijunwei
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class LogConfig {

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 对所有使用注解{@link org.springframework.web.bind.annotation.RestController}的class记录日志
     * @param joinPoint 切入点
     * @return 接入点的执行结果
     * @throws Throwable 切入点执行的异常
     */
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Log logAnnotation = getLog(joinPoint.getSignature(), joinPoint.getTarget());
        if (Objects.isNull(logAnnotation)) {
            return joinPoint.proceed(joinPoint.getArgs());
        }

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.isNull(requestAttributes) ? null : requestAttributes.getRequest();
        if (!logAnnotation.ignoreRequest()) {
            String req = LogHelper.appendUrl(request) + " 入参:" + objectMapper.writeValueAsString(this.filterArgs(joinPoint.getArgs()));
            log.info("logName: {}, request: {}", logAnnotation.value(), req);
        }

        long start = System.currentTimeMillis();
        Object resp = joinPoint.proceed(joinPoint.getArgs());
        long end = System.currentTimeMillis();

        if (!logAnnotation.ignoreResponse()) {
            log.info("logName: {}, response: {}, 执行耗时: {}ms", logAnnotation.value(), this.respToString(resp), (end - start));
        }
        return resp;
    }

    /**
     * 获取自定义{@link Log}
     * @param signature 切入点签名
     * @param obj 切入的对象
     * @return 返回Log注解
     */
    private Log getLog(Signature signature, Object obj) {
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            Log log = method.getAnnotation(Log.class);
            if (Objects.nonNull(log)) {
                return log;
            }
        }

        return obj.getClass().getAnnotation(Log.class);
    }

    /**
     * 过滤不要用的参数类型
     * @param objects 参数对象列表
     * @return 过滤后的结果参数
     */
    private List<Object> filterArgs(Object[] objects) {
        if (ArrayUtils.isEmpty(objects)) {
            return Collections.emptyList();
        }

        return Arrays.stream(objects)
                .filter(obj -> !(obj instanceof MultipartFile)
                        && !(obj instanceof HttpServletResponse)
                        && !(obj instanceof HttpServletRequest)
                        && !(obj instanceof BindingResult)
                )
                .collect(Collectors.toList());
    }

    /**
     * 如果响应已是字符串，直接返回字符串，否则再调用序列化方法为字符串
     * @param resp 响应体
     * @return 字符串
     * @throws JsonProcessingException
     */
    private String respToString(Object resp) throws JsonProcessingException {
        if (Objects.isNull(resp)) {
            return null;
        }

        return resp instanceof String ? resp.toString() : objectMapper.writeValueAsString(resp);
    }

}
