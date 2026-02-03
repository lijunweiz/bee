package cn.unminded.bee.turn.dto.aviator.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author lijunwei
 */
@Data
public class EvalContentRequest {

    @NotBlank(message = "表达式不能为空")
    private String expression;

    /**
     * 表达式用到的上下文数据
     */
    private Map<String, Object> context;

}
