package cn.unminded.bee.manage.controller;

import cn.unminded.bee.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author lijunwei
 */
@Slf4j
@RequestMapping("/manage/flow")
@RestController
public class FlowController {


    @GetMapping("/query")
    public Result query(@RequestParam("flowId") String flowId, @RequestParam("graphId") String graphId) {

        return Result.ok();
    }

    @PostMapping("/add")
    public Result add(@RequestParam("flowId") String flowId, @RequestParam("graphId") String graphId) {

        return Result.ok();
    }

}
