package cn.unminded.bee.core.util;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BeeUtilsTest {

    @Test
    void testReadProperties() {
        Object ruleArithmeticOperator = BeeUtils.getBeeProperties().get("ruleArithmeticOperator");
        System.out.println("ruleArithmeticOperator: " + ruleArithmeticOperator);
        assertTrue(Objects.nonNull(ruleArithmeticOperator));

        Object ruleCompareOperator = BeeUtils.getBeeProperties().get("ruleCompareOperator");
        System.out.println("ruleCompareOperator: " + ruleCompareOperator);
        assertTrue(Objects.nonNull(ruleCompareOperator));

        Object ruleLogicalOperator = BeeUtils.getBeeProperties().get("ruleLogicalOperator");
        System.out.println("ruleLogicalOperator: " + ruleLogicalOperator);
        assertTrue(Objects.nonNull(ruleLogicalOperator));
    }

}