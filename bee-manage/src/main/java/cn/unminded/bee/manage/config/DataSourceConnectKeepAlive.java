package cn.unminded.bee.manage.config;

import cn.hutool.core.thread.NamedThreadFactory;
import cn.unminded.bee.persistence.mapper.VariableMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The last packet sent successfully to the server was 32,241 milliseconds ago. is longer than the server configured
 * value of 'wait_timeout'. You should consider either expiring and/or testing connection validity before use in your
 * application, increasing the server configured values for client timeouts, or using the Connector/J connection
 * property 'autoReconnect=true' to avoid this problem
 * @author lijunwei
 */
@Configuration
public class DataSourceConnectKeepAlive {

    @Resource
    private Environment environment;

    @Resource
    private VariableMapper variableMapper;

    @Value("${bee.db.keepAliveProfile:}")
    private String keepAliveProfile;

    @PostConstruct
    public void connectKeepAlive() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (StringUtils.isNotBlank(keepAliveProfile)
                && ArrayUtils.isNotEmpty(activeProfiles)
                && Arrays.stream(activeProfiles).anyMatch(x -> x.equals(keepAliveProfile))) {
            ScheduledExecutorService keepAliveScheduler = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("keepAlive", true));
            keepAliveScheduler.scheduleWithFixedDelay(() -> variableMapper.connectKeepAlive(), 1, 5, TimeUnit.SECONDS);
        }
    }

}
