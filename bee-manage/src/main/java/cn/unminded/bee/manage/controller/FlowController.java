package cn.unminded.bee.manage.controller;

import cn.unminded.bee.common.Result;
import cn.unminded.bee.common.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author lijunwei
 */
@Log("决策流控制")
@Slf4j
@RequestMapping("/manage/flow")
@RestController
public class FlowController {

    @Log("决策流查询")
    @GetMapping("/query")
    public Result query(@RequestParam("flowId") String flowId, @RequestParam("graphId") String graphId) {

        return Result.ok();
    }

    @Log("添加决策流")
    @PostMapping("/add")
    public Result add(@RequestParam("flowId") String flowId, @RequestParam("graphId") String graphId) {

        return Result.ok();
    }

}
