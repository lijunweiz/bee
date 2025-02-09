package cn.unminded.bee.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lijunwei
 */
@Getter
@AllArgsConstructor
public enum ModelTreeNodeTypeEnum {

    NO(0, "非叶子节点"),

    YES(1, "叶子节点")

    ;

    private Integer code;

    private String desc;
}
