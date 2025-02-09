package cn.unminded.bee.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lijunwei
 */
@Getter
@AllArgsConstructor
public enum ModelStatusEnum {

    CREATE(0, "新建"),

    RUNNING(1, "启用"),

    STOP(2, "启用"),

    DEPRECATED(3, "废弃"),

    ;

    private Integer code;

    private String desc;
}
