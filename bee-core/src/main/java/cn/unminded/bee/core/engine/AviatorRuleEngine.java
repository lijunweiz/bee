package cn.unminded.bee.core.engine;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;

import java.util.Map;

/**
 * @author lijunwei
 */
public class AviatorRuleEngine implements RuleEngine {

    private static final AviatorEvaluatorInstance INSTANCE = AviatorEvaluator.getInstance();

    @Override
    public void compile(String expression) {
        INSTANCE.compile(expression, true);
    }

    @Override
    public Object execute(String expression) {
        return INSTANCE.execute(expression);
    }

    @Override
    public Object execute(String expression, Map<String, Object> env) {
        return INSTANCE.execute(expression, env);
    }

    @Override
    public Object execute(String expression, Map<String, Object> env, boolean cached) {
        return INSTANCE.execute(expression, env, cached);
    }

    @Override
    public void invalidateCache(String expression) {
        INSTANCE.invalidateCache(expression);
    }

}
