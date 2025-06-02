package cn.unminded.bee.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lijunwei
 */
class RuleExecutorTest {

    RuleExecutor ruleExecutor;

    @BeforeEach
    void ruleExecutor() {
        ruleExecutor = new RuleExecutor();
    }

    @Test
    void compile() {
        ruleExecutor.compile("add", "x+y");
        Object result = ruleExecutor.executeCache("add", Map.of("x", 1, "y", 2));
        assertEquals(3L, result);
    }

}