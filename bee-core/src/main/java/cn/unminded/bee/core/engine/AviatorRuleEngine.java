package cn.unminded.bee.core.engine;

import cn.unminded.bee.core.exception.BeeCoreException;
import com.googlecode.aviator.*;
import com.googlecode.aviator.runtime.JavaMethodReflectionFunctionMissing;

import java.util.Map;
import java.util.Objects;

/**
 * @author lijunwei
 */
public class AviatorRuleEngine implements RuleEngine {

    private static final AviatorEvaluatorInstance INSTANCE = AviatorEvaluator.getInstance();

    /**
     * 缓存编译结果
     */
    private static final boolean CACHED_ENABLE = true;

    /**
     * 缓存容量
     */
    private static final int CACHE_CAPACITY = 10000;

    static {
        INSTANCE.setOption(Options.FEATURE_SET, Feature.getFullFeatures());
        INSTANCE.setFunctionMissing(JavaMethodReflectionFunctionMissing.getInstance());
        INSTANCE.setCachedExpressionByDefault(CACHED_ENABLE);
        INSTANCE.useLRUExpressionCache(CACHE_CAPACITY);
    }

    public static AviatorEvaluatorInstance getInstance() {
        return INSTANCE;
    }

    @Override
    public Expression compile(String expression) {
        return this.compile(expression, expression, CACHED_ENABLE);
    }

    @Override
    public Expression compile(String cacheKey, String expression) {
        return this.compile(cacheKey, expression, CACHED_ENABLE);
    }

    @Override
    public Expression compile(String cacheKey, String expression, final boolean cached) {
        return getInstance().compile(cacheKey, expression, cached);
    }

    @Override
    public Object executeCache(String cacheKey, Map<String, Object> env) {
        Expression expression = getInstance().getCachedExpressionByKey(cacheKey);
        if (Objects.isNull(expression)) {
            throw new BeeCoreException("expression not found: " + cacheKey);
        }
        return expression.execute(env);
    }

    @Override
    public Object execute(String expression) {
        return this.execute(expression, null);
    }

    @Override
    public Object execute(String expression, Map<String, Object> env) {
        return this.execute(expression, env, CACHED_ENABLE);
    }

    @Override
    public Object execute(String expression, Map<String, Object> env, boolean cached) {
        if (cached && getInstance().isExpressionCached(expression)) {
            return getInstance().getCachedExpression(expression).execute(env);
        }
        return getInstance().execute(expression, env, cached);
    }

    @Override
    public void invalidateCache(String expression) {
        getInstance().invalidateCache(expression);
    }

}
