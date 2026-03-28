package cn.unminded.bee.common.config;

import cn.unminded.bee.core.RuleExecutor;
import cn.unminded.bee.core.engine.AviatorRuleEngine;
import cn.unminded.bee.core.engine.RuleEngine;
import cn.unminded.bee.core.engine.compiler.DynamicCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijunwei
 */
@Configuration
public class BeeAppConfig {

    @Bean
    public DynamicCompiler dynamicCompiler() {
        return new DynamicCompiler();
    }

    @Bean
    public RuleEngine ruleEngine() {
        return new AviatorRuleEngine();
    }

    @Bean
    public RuleExecutor ruleExecutor(@Autowired RuleEngine ruleEngine) {
        return new RuleExecutor(ruleEngine);
    }

}
