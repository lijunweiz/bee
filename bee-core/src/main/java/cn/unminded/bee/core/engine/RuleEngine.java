package cn.unminded.bee.core.engine;

import com.googlecode.aviator.Expression;

import java.util.Map;

/**
 * 规则引擎, 用于解析规则表达式
 * @author lijunwei
 */
public interface RuleEngine {

    Expression compile(String expression);

    Expression compile(String cacheKey, String expression);

    Expression compile(String cacheKey, String expression, boolean cached);

    Object executeCache(String cacheKey, Map<String, Object> env);

    Object execute(String expression);

    Object execute(String expression, Map<String, Object> env);

    Object execute(String expression, Map<String, Object> env, boolean cached);

    void invalidateCache(String expression);


}
