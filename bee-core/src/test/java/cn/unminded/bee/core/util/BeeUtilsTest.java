package cn.unminded.bee.core.util;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BeeUtilsTest {

    @Test
    void testReadProperties() {
        Object ruleOperatorArithmetic = BeeUtils.getBeeProperties().get("rule.operator.arithmetic");
        System.out.println("rule.operator.arithmetic: " + ruleOperatorArithmetic);
        assertTrue(Objects.nonNull(ruleOperatorArithmetic));

        Object ruleOperatorCompare = BeeUtils.getBeeProperties().get("rule.operator.compare");
        System.out.println("rule.operator.compare: " + ruleOperatorCompare);
        assertTrue(Objects.nonNull(ruleOperatorCompare));

        Object ruleOperatorLogical = BeeUtils.getBeeProperties().get("rule.operator.logical");
        System.out.println("rule.operator.logical: " + ruleOperatorLogical);
        assertTrue(Objects.nonNull(ruleOperatorLogical));
    }

}