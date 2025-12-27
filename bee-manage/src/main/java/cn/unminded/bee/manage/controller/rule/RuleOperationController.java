package cn.unminded.bee.manage.controller.rule;

import cn.unminded.bee.common.Result;
import cn.unminded.bee.common.annotation.Log;
import cn.unminded.bee.common.constant.RuleGroupEnum;
import cn.unminded.bee.core.rule.RuleOperator;
import cn.unminded.bee.core.util.BeeUtils;
import cn.unminded.bee.persistence.criteria.QueryRuleCriteria;
import cn.unminded.bee.persistence.entity.RuleRecordEntity;
import cn.unminded.bee.service.rule.RuleOperationService;
import cn.unminded.bee.turn.dto.rule.response.RuleSetTreeDataResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Resource
    private RuleOperationService ruleOperationService;

    @Log("获取规则运算符")
    @GetMapping("/operator")
    public Result ruleOperator(@RequestParam(name = "type", required = false, defaultValue = "arithmetic,compare")
                                   List<String> operatorTypeList) {
        List<RuleOperator> operatorList = Lists.newArrayList();
        Properties properties = BeeUtils.getBeeProperties();
        if (CollectionUtils.isNotEmpty(operatorTypeList)) {
            for (String operatorType : operatorTypeList) {
                String property = properties.getProperty("rule.operator." + operatorType);
                operatorList.addAll(JSON.parseObject(property, new TypeReference<ArrayList<RuleOperator>>() {}));
            }
        }

        return Result.ok(operatorList);
    }

    @Log("规则树")
    @GetMapping("/list")
    public Result ruleList(@RequestParam(name = "modelId") Long modelId) {
        Map<String, Object> data = new HashMap<>();
        // 1-决策组
        List<RuleSetTreeDataResponse> treeDataResult = new ArrayList<>();
        RuleSetTreeDataResponse decisionGroup = new RuleSetTreeDataResponse();
        decisionGroup.setRuleSetId(Long.valueOf(RuleGroupEnum.DECISION_GROUP.getCode()));
        decisionGroup.setLabel(RuleGroupEnum.DECISION_GROUP.getName());
        QueryRuleCriteria criteria = new QueryRuleCriteria().setModelId(modelId);
        List<RuleRecordEntity> decisionList = ruleOperationService.query(criteria.setRuleGroup(RuleGroupEnum.DECISION_GROUP.getCode()));
        decisionGroup.setTreeData(this.buildRuleSetTreeData(decisionList));
        // 2-监控组
        RuleSetTreeDataResponse rejectGroup = new RuleSetTreeDataResponse();
        rejectGroup.setRuleSetId(Long.valueOf(RuleGroupEnum.REJECT_GROUP.getCode()));
        rejectGroup.setLabel(RuleGroupEnum.REJECT_GROUP.getName());
        List<RuleRecordEntity> rejectList = ruleOperationService.query(criteria.setRuleGroup(RuleGroupEnum.REJECT_GROUP.getCode()));
        rejectGroup.setTreeData(this.buildRuleSetTreeData(rejectList));
        // 3-拒绝组
        RuleSetTreeDataResponse monitorGroup = new RuleSetTreeDataResponse();
        monitorGroup.setRuleSetId(Long.valueOf(RuleGroupEnum.MONITOR_GROUP.getCode()));
        monitorGroup.setLabel(RuleGroupEnum.MONITOR_GROUP.getName());
        List<RuleRecordEntity> monitorList = ruleOperationService.query(criteria.setRuleGroup(RuleGroupEnum.MONITOR_GROUP.getCode()));
        monitorGroup.setTreeData(this.buildRuleSetTreeData(monitorList));

        treeDataResult.add(decisionGroup);
        treeDataResult.add(rejectGroup);
        treeDataResult.add(monitorGroup);
        data.put("treeData", treeDataResult);// 树形列表

        return Result.ok(data);
    }

    private List<RuleSetTreeDataResponse> buildRuleSetTreeData(List<RuleRecordEntity> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        return list.stream().map(x -> {
            RuleSetTreeDataResponse response = new RuleSetTreeDataResponse();
            response.setRuleSetId(x.getId());
            response.setLabel(x.getRuleName());
            return response;
        }).collect(Collectors.toList());
    }

    @Log("规则详情")
    @GetMapping("/list/detail")
    public Result ruleDetail(@RequestParam(name = "ruleId", required = false) Long ruleId, @RequestParam(name = "ruleName", required = false) String ruleName) {
        List<RuleRecordEntity> ruleList = ruleOperationService.query(new QueryRuleCriteria().setId(ruleId).setRuleName(ruleName));
        if (CollectionUtils.isNotEmpty(ruleList)) {
            return Result.ok(ruleList.get(0));
        }

        return Result.ok();
    }

    @Log("创建规则集")
    @PostMapping("/create")
    public Result createRuleSet(@RequestBody Map<String, Object> params) {

        return Result.ok();
    }

    @Log("创建规则集")
    @PostMapping("/create/detail")
    public Result createRuleDetail(@RequestBody Map<String, Object> params) {

        return Result.ok();
    }

}
