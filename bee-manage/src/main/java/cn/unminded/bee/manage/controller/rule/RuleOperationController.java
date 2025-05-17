package cn.unminded.bee.manage.controller.rule;

import cn.unminded.bee.common.Result;
import cn.unminded.bee.common.annotation.Log;
import cn.unminded.bee.core.rule.RuleOperator;
import cn.unminded.bee.core.util.BeeUtils;
import cn.unminded.bee.manage.dto.rule.response.RuleSetTreeDataResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 规则运算符
 * @author lijunwei
 */
@Log("规则配置")
@Slf4j
@RequestMapping("/manage/rule")
@RestController
public class RuleOperationController {

    @Log("获取规则运算符")
    @GetMapping("/operator")
    public Result ruleOperator(@RequestParam(name = "type", required = false, defaultValue = "arithmetic,compare")
                                   List<String> operatorTypeList) {
        List<String> list = Lists.newArrayList();
        Properties properties = BeeUtils.getBeeProperties();
        if (CollectionUtils.isNotEmpty(operatorTypeList)) {
            List<RuleOperator> operatorList = Lists.newArrayList();
            for (String operatorType : operatorTypeList) {
                String property = properties.getProperty("rule.operator." + operatorType);
                operatorList.addAll(JSON.parseObject(property, new TypeReference<ArrayList<RuleOperator>>() {}));
            }
            list = operatorList.stream().map(RuleOperator::getItemDesc).collect(Collectors.toList());
        }

        return Result.ok(list);
    }

    @Log("规则树")
    @GetMapping("/list")
    public Result ruleList(@RequestParam("modelId") Long modelId) {
        Map<String, Object> data = new HashMap<>();

        List<RuleSetTreeDataResponse> treeDataResult = new ArrayList<>();
        RuleSetTreeDataResponse decisionGroup = new RuleSetTreeDataResponse();
        decisionGroup.setRuleSetId(1L);
        decisionGroup.setLabel("决策组");
        List<RuleSetTreeDataResponse> decisionGroupTreeData = new ArrayList<>();
        decisionGroupTreeData.add(new RuleSetTreeDataResponse(2L, "决策组1", null));
        decisionGroupTreeData.add(new RuleSetTreeDataResponse(3L, "决策组2", null));
        decisionGroup.setTreeData(decisionGroupTreeData);


        RuleSetTreeDataResponse rejectGroup = new RuleSetTreeDataResponse();
        rejectGroup.setRuleSetId(4L);
        rejectGroup.setLabel("拒绝组");
        RuleSetTreeDataResponse monitorGroup = new RuleSetTreeDataResponse();



        treeDataResult.add(decisionGroup);
        treeDataResult.add(rejectGroup);
        treeDataResult.add(monitorGroup);


        data.put("treeData", treeDataResult);// 树形列表

        return Result.ok(data);
    }

    @Log("创建规则集")
    @PostMapping("/create")
    public Result createRuleSet(@RequestBody Map<String, Object> params) {

        return Result.ok();
    }

}
