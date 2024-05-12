package cn.unminded.bee.common.config;

import cn.unminded.bee.common.annotation.Log;
import cn.unminded.bee.common.util.LogHelper;
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
 * @author lijunwei
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class LogConfig {

    @Resource
    private ObjectMapper objectMapper;

    @Around("@within(org.springframework.web.bind.annotation.RestController)" +
            "||@within(org.springframework.stereotype.Controller)")
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
            log.info("logName: {}, response: {}, 执行耗时: {}ms", logAnnotation.value(), objectMapper.writeValueAsString(resp), (end - start));
        }
        return resp;
    }

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
     * @Pointcut("execution(public * cn.unminded.bee.*.controller..*.*(..))") controller切入
     * @Pointcut("@annotation(cn.unminded.bee.common.annotation.Log)") 方法切入
     */
//    @Pointcut("execution(public * cn.unminded.bee.*.controller..*.*(..))")
//    public void log() {
//
//    }

//    @Around("log()")
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//
//    }

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

}
