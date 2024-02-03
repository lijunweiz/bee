package cn.unminded.bee.api.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/mock")
@RestController
public class MockController {


    @GetMapping(value = "/name")
    public Map<String, Object> name() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "李四");
        return map;
    }

    @GetMapping("/graph")
    public Map<String, Object> mock() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "李四");

        String nodes = "[\n" +
                "        {\n" +
                "          id: \"init_1azos4vjtni8000\",\n" +
                "          type: \"event-node\",\n" +
                "          x: 100,\n" +
                "          y: 80,\n" +
                "          properties: {\n" +
                "            componentId: \"page_init\",\n" +
                "            componentName: \"pageInit\",\n" +
                "            name: \"页面初始化\",\n" +
                "          },\n" +
                "        },\n" +
                "        {\n" +
                "          id: \"logic_98ujn64fwy00000\",\n" +
                "          type: \"common-node\",\n" +
                "          x: 260,\n" +
                "          y: 80,\n" +
                "          properties: {\n" +
                "            type: \"dataSource\",\n" +
                "            name: \"请求数据\",\n" +
                "            componentName: \"dataSource\",\n" +
                "          },\n" +
                "        },\n" +
                "        {\n" +
                "          id: \"logic_d5nttghd60o0000\",\n" +
                "          type: \"common-node\",\n" +
                "          x: 420,\n" +
                "          y: 80,\n" +
                "          properties: {\n" +
                "            type: \"pageJump\",\n" +
                "            name: \"页面跳转\",\n" +
                "            componentName: \"pageJump\",\n" +
                "          },\n" +
                "        },\n" +
                "        {\n" +
                "          id: \"logic_fsygrbs67t40000\",\n" +
                "          type: \"common-node\",\n" +
                "          x: 420,\n" +
                "          y: 140,\n" +
                "          properties: {\n" +
                "            type: \"dataConvert\",\n" +
                "            name: \"数据转换1\",\n" +
                "            componentName: \"dataConvert\",\n" +
                "            dc: {\n" +
                "              convertList: [\n" +
                "                {\n" +
                "                  key: \"key1\",\n" +
                "                  value: {\n" +
                "                    type: \"dataConvert\",\n" +
                "                    nodeId: \"logic_9v5h5c4ium80000\",\n" +
                "                  },\n" +
                "                },\n" +
                "                { key: \"key2\" },\n" +
                "                { key: \"key3\" },\n" +
                "              ],\n" +
                "              convertCode: \"return [1, 2, 3]\",\n" +
                "            },\n" +
                "          },\n" +
                "        },\n" +
                "        {\n" +
                "          id: \"logic_9v5h5c4ium80000\",\n" +
                "          type: \"common-node\",\n" +
                "          x: 580,\n" +
                "          y: 140,\n" +
                "          properties: {\n" +
                "            type: \"dataConvert\",\n" +
                "            name: \"数据转换2\",\n" +
                "            componentName: \"dataConvert\",\n" +
                "            dc: { convertList: [], convertCode: \"return [3, 4, 5]\" },\n" +
                "          },\n" +
                "        },\n" +
                "        {\n" +
                "          id: \"logic_73gus8kpsgk0000\",\n" +
                "          type: \"common-node\",\n" +
                "          x: 260,\n" +
                "          y: 140,\n" +
                "          properties: {\n" +
                "            type: \"dataConvert\",\n" +
                "            name: \"数据转换\",\n" +
                "            componentName: \"dataConvert\",\n" +
                "            dc: {\n" +
                "              convertList: [\n" +
                "                {\n" +
                "                  key: \"key1\",\n" +
                "                  value: {\n" +
                "                    type: \"componentProp\",\n" +
                "                    prop: \"value\",\n" +
                "                    field: \"\",\n" +
                "                    componentId: \"InputNumber_azsj\",\n" +
                "                    dataType: \"number\",\n" +
                "                    componentName: \"数字输入框\",\n" +
                "                    propName: \"当前值\",\n" +
                "                  },\n" +
                "                },\n" +
                "                {\n" +
                "                  key: \"key2\",\n" +
                "                  value: {\n" +
                "                    type: \"componentProp\",\n" +
                "                    prop: \"value\",\n" +
                "                    field: \"\",\n" +
                "                    componentId: \"TimePicker_sxpq\",\n" +
                "                    dataType: \"number\",\n" +
                "                    componentName: \"时间选择器\",\n" +
                "                    propName: \"值\",\n" +
                "                  },\n" +
                "                },\n" +
                "                {\n" +
                "                  key: \"\",\n" +
                "                  value: { type: \"dataConvert\", dataType: \"string\" },\n" +
                "                },\n" +
                "              ],\n" +
                "              convertCode:\n" +
                "                \"const my_new_code_here = \\\"Blabla\\\"\\n\\nconsole.log('this.12123')\\n\\nreturn my_new_code_here\",\n" +
                "            },\n" +
                "          },\n" +
                "        },\n" +
                "      ]";

        String edges = "[\n" +
                "        {\n" +
                "          id: \"logic_582qup4obxg0000\",\n" +
                "          type: \"logic-line\",\n" +
                "          sourceNodeId: \"logic_fsygrbs67t40000\",\n" +
                "          targetNodeId: \"logic_9v5h5c4ium80000\",\n" +
                "          startPoint: { x: 470, y: 140 },\n" +
                "          endPoint: { x: 530, y: 140 },\n" +
                "          properties: {},\n" +
                "          pointsList: [\n" +
                "            { x: 470, y: 140 },\n" +
                "            { x: 530, y: 140 },\n" +
                "          ],\n" +
                "        },\n" +
                "        {\n" +
                "          id: \"logic_3s6nbc92cx00000\",\n" +
                "          type: \"logic-line\",\n" +
                "          sourceNodeId: \"logic_98ujn64fwy00000\",\n" +
                "          targetNodeId: \"logic_d5nttghd60o0000\",\n" +
                "          startPoint: { x: 310, y: 80 },\n" +
                "          endPoint: { x: 370, y: 80 },\n" +
                "          properties: {},\n" +
                "          pointsList: [\n" +
                "            { x: 310, y: 80 },\n" +
                "            { x: 370, y: 80 },\n" +
                "          ],\n" +
                "        },\n" +
                "        {\n" +
                "          id: \"logic_93xrjtjbzs40000\",\n" +
                "          type: \"logic-line\",\n" +
                "          sourceNodeId: \"logic_98ujn64fwy00000\",\n" +
                "          targetNodeId: \"logic_fsygrbs67t40000\",\n" +
                "          startPoint: { x: 310, y: 80 },\n" +
                "          endPoint: { x: 370, y: 140 },\n" +
                "          properties: {},\n" +
                "          pointsList: [\n" +
                "            { x: 310, y: 80 },\n" +
                "            { x: 334, y: 80 },\n" +
                "            { x: 334, y: 140 },\n" +
                "            { x: 370, y: 140 },\n" +
                "          ],\n" +
                "        },\n" +
                "        {\n" +
                "          id: \"logic_fz4h8dc8yds0000\",\n" +
                "          type: \"logic-line\",\n" +
                "          sourceNodeId: \"init_1azos4vjtni8000\",\n" +
                "          targetNodeId: \"logic_98ujn64fwy00000\",\n" +
                "          startPoint: { x: 150, y: 80 },\n" +
                "          endPoint: { x: 210, y: 80 },\n" +
                "          properties: {},\n" +
                "          pointsList: [\n" +
                "            { x: 150, y: 80 },\n" +
                "            { x: 210, y: 80 },\n" +
                "          ],\n" +
                "        },\n" +
                "        {\n" +
                "          id: \"logic_2zcnfddo2ng0000\",\n" +
                "          type: \"logic-line\",\n" +
                "          sourceNodeId: \"init_1azos4vjtni8000\",\n" +
                "          targetNodeId: \"logic_73gus8kpsgk0000\",\n" +
                "          startPoint: { x: 150, y: 80 },\n" +
                "          endPoint: { x: 210, y: 140 },\n" +
                "          properties: {},\n" +
                "          pointsList: [\n" +
                "            { x: 150, y: 80 },\n" +
                "            { x: 174, y: 80 },\n" +
                "            { x: 174, y: 140 },\n" +
                "            { x: 210, y: 140 },\n" +
                "          ],\n" +
                "        },\n" +
                "      ]" ;

        map.put("nodes", JSON.parseArray(nodes));
        map.put("edges", JSON.parseArray(edges));


        return map;
    }

}
