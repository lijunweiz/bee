package cn.unminded.bee.manage.controller.rule;

import cn.unminded.bee.common.Result;
import cn.unminded.bee.common.annotation.Log;
import cn.unminded.bee.core.rule.MathOperator;
import cn.unminded.bee.core.util.BeeUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 规则运算符
 * @author lijunwei
 */
@Log("规则编辑")
@Slf4j
@RequestMapping("/manage/rule")
@RestController
public class RuleOperationController {

    @Log("获取运算符")
    @GetMapping(value = "/operator")
    public Result operatorList(@RequestParam("operatorTypeName") String operatorTypeName) {
        String property = BeeUtils.getBeeProperties().getProperty(operatorTypeName);
        if (StringUtils.isBlank(property)) {
            return Result.fail("未找到变量: " + operatorTypeName);
        }

        return Result.ok(JSON.parseObject(property, new TypeReference<List<MathOperator>>(){}));
    }

}
