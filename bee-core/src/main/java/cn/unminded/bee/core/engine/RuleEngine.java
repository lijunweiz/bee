package cn.unminded.bee.core.engine;

import java.util.Map;

/**
 * 规则引擎, 用于解析规则表达式
 * @author lijunwei
 */
public interface RuleEngine {

    void compile(String expression);

    Object execute(String expression);

    Object execute(String expression, Map<String, Object> env);

    Object execute(String expression, Map<String, Object> env, boolean cached);

    void invalidateCache(String expression);


}
