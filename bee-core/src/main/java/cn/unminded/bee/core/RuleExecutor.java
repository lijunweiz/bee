package cn.unminded.bee.core;

import cn.unminded.bee.core.engine.AviatorRuleEngine;
import cn.unminded.bee.core.engine.RuleEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @author lijunwei
 */
public class RuleExecutor {

    private static final Logger logger = LoggerFactory.getLogger(RuleExecutor.class);

    private final RuleEngine ruleEngine = new AviatorRuleEngine();

    private static final Properties EXECUTORS_EXPRESSIONS = new Properties();

    {
        warmUp();
    }

    private void warmUp() {
        loadScript();
        prepareCompile();
    }

    private void loadScript() {
        try (InputStream inputStream = RuleExecutor.class.getClassLoader().getResourceAsStream("bee-script.properties")){
            if (Objects.nonNull(inputStream)) {
                EXECUTORS_EXPRESSIONS.load(inputStream);
            }
        } catch (IOException e) {
            logger.warn("bee-script.properties加载失败: {}", e.getMessage());
        }
    }

    private void prepareCompile() {
        if (!EXECUTORS_EXPRESSIONS.isEmpty()) {
            for (Map.Entry<Object, Object> entry : EXECUTORS_EXPRESSIONS.entrySet()) {
                ruleEngine.compile(entry.getValue().toString());
            }
        }
    }

    public void compile(String expression) {
        ruleEngine.compile(expression);
    }

    public Object execute(String expression) {
        return ruleEngine.execute(expression);
    }

    public Object execute(String expression, Map<String, Object> data) {
        return ruleEngine.execute(expression, data);
    }

    public Object execute(String expression, Map<String, Object> data, boolean cached) {
        return ruleEngine.execute(expression, data, cached);
    }

    public void invalidateCache(String expression) {
        ruleEngine.invalidateCache(expression);
    }

}
