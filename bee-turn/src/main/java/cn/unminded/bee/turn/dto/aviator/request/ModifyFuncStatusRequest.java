package cn.unminded.bee.turn.dto.aviator.request;

import lombok.Data;

/**
 * @author lijunwei
 */
@Data
public class ModifyFuncStatusRequest {

    private Long id;

    private String  funcNameEn;

    private Integer version;
}
