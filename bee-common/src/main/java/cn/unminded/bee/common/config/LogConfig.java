package cn.unminded.bee.common.config;

import cn.unminded.bee.common.util.LogHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.isNull(requestAttributes) ? null : requestAttributes.getRequest();
        String req = LogHelper.appendUrl(request) + " " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()
                + " request: " + objectMapper.writeValueAsString(this.filterArgs(joinPoint.getArgs()));
        long start = System.currentTimeMillis();
        Object resp = joinPoint.proceed(joinPoint.getArgs());
        long end = System.currentTimeMillis();
        log.info("request: {}, response: {}, 执行耗时: {}ms", req, objectMapper.writeValueAsString(resp), (end - start));
        return resp;
    }

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
