package cn.unminded.bee.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/g")
@RestController
public class GraphController {

    @Resource
    private MockController mockController;

    @GetMapping("/name")
    public Map<String, Object> resp() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "李四");
        return map;
    }

    @GetMapping("/query")
    public Map<String, Object> graphData(@RequestParam("gid") String gid) {
        return mockController.mock();
    }

}
