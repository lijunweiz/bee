package cn.unminded.bee.service.aviator;

import cn.unminded.bee.persistence.entity.FunctionEntity;
import cn.unminded.bee.turn.dto.aviator.request.AddAviatorFuncRequest;
import cn.unminded.bee.turn.dto.aviator.response.AviatorFuncResponse;

import java.util.List;
import java.util.Map;

/**
 * @author lijunwei
 */
public interface AviatorService {

    Map<String, List<AviatorFuncResponse>> funMapList();

    void addFunc(AddAviatorFuncRequest func);

    void updateFunc(FunctionEntity functionEntity);

    Object eval(String expression, Map<String, Object> data);

}
