package cn.unminded.bee.core.util;

import cn.unminded.bee.core.engine.AviatorRuleEngine;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import org.junit.jupiter.api.Test;

import java.util.*;

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

    @Test
    void testAviatorFunc() {
        AviatorEvaluatorInstance instance = AviatorRuleEngine.getInstance();
        instance.getFuncMap().keySet().forEach(System.out::println);
        System.out.println("*******************************\n");

        List<String> unKnown = new ArrayList<>();
        Map<String, List<String>> funcListMap = new HashMap<>();
        for (String name : instance.getFuncMap().keySet()) {
            String[] split = name.split("\\.");
            if (split.length == 2) {
                if (funcListMap.containsKey(split[0])) {
                    funcListMap.get(split[0]).add(name);
                } else {
                    List<String> list = new ArrayList<>();
                    list.add(name);
                    funcListMap.put(split[0], list);
                }
            } else {
                unKnown.add(name);
            }
        }

        System.out.println(JSON.toJSONString(funcListMap, SerializerFeature.PrettyFormat));
        System.out.println(JSON.toJSONString(unKnown));

        assertTrue(funcListMap.containsKey("string"));
    }

}