package cn.unminded.bee.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lijunwei
 */
@ToString
@Getter
@AllArgsConstructor
public enum VariableStatusEnum {

    DEFAULT(0, "新建"),
    EDITING(1, "编辑"),
    RUNNING(2, "运行"),
    STOP(3, "停用"),
    DELETE(4, "删除"),

    ;

    private final Integer status;

    private final String desc;

    public static Optional<VariableStatusEnum> getInstance(Integer status) {
        if (Objects.isNull(status) || status < 0) {
            return Optional.empty();
        }

        for (VariableStatusEnum statusEnum : values()) {
            if (Objects.equals(status, statusEnum.getStatus())) {
                return Optional.of(statusEnum);
            }
        }

        return Optional.empty();
    }

    public static Map<Integer, String> toMap() {
        return Arrays.stream(values())
                .collect(Collectors.toMap(VariableStatusEnum::getStatus, VariableStatusEnum::getDesc));
    }

    public static List<VariableStatusEnum> toList() {
        return Arrays.asList(values());
    }

}
