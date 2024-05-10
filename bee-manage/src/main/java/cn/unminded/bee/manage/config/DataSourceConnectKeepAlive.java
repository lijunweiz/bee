package cn.unminded.bee.manage.config;

import cn.unminded.bee.persistence.mapper.VariableMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * The last packet sent successfully to the server was 32,241 milliseconds ago. is longer than the server configured
 * value of 'wait_timeout'. You should consider either expiring and/or testing connection validity before use in your
 * application, increasing the server configured values for client timeouts, or using the Connector/J connection
 * property 'autoReconnect=true' to avoid this problem
 * @author lijunwei
 */
@EnableScheduling
@Configuration
public class DataSourceConnectKeepAlive {

    @Resource
    private VariableMapper variableMapper;

    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    public void connectKeepAlive() {
        variableMapper.connectKeepAlive();
    }

}
