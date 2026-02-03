package cn.unminded.bee.manage.controller.aviator;

import cn.unminded.bee.core.util.HttpUtils;
import cn.unminded.bee.turn.dto.aviator.request.EvalContentRequest;
import cn.unminded.bee.turn.dto.aviator.request.AddAviatorFuncRequest;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

class AviatorControllerTest {

    @Test
    void funMapList() throws IOException, InterruptedException {
        String json = HttpUtils.get("http://localhost:8888/manage/aviator/funMapList");
        System.out.println("************************\n" + json);
        Thread.sleep(1000);
    }

    @Test
    void addFunc() throws IOException, InterruptedException {
        String func = IOUtils.toString(getClass().getResourceAsStream("/rule/ProductCodeConvert.java"), Charset.defaultCharset());
        AddAviatorFuncRequest addAviatorFuncRequest = new AddAviatorFuncRequest();
        addAviatorFuncRequest.setFuncNameEn("productCodeConvert");
        addAviatorFuncRequest.setFuncNameZh("产品编码转换");
        addAviatorFuncRequest.setFuncDesc("产品编码转换");
        addAviatorFuncRequest.setFuncType(2);
        addAviatorFuncRequest.setFunc(func);
        String json = HttpUtils.post("http://localhost:8888/manage/aviator/addFunc", addAviatorFuncRequest);
        System.out.println("************************\n" + json);
        Thread.sleep(1000);
    }

    @Test
    void evalExpression() throws IOException, InterruptedException {
        EvalContentRequest evalContentRequest = new EvalContentRequest();
        evalContentRequest.setExpression("bee.productCodeConvert(productCode)");
        evalContentRequest.setContext(Map.of("productCode", "acc"));
        String json = HttpUtils.post("http://localhost:8888/manage/aviator/eval", evalContentRequest);
        System.out.println("************************\n" + json);
        Thread.sleep(1000);
    }
}