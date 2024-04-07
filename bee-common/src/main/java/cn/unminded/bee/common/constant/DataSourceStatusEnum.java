package cn.unminded.bee.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;

/**
 * @author lijunwei
 */
@Getter
@AllArgsConstructor
public enum DataSourceStatusEnum {

    DEFAULT(0, "新建"),
    RUNNING(1, "运行中"),

    ;

    private final Integer status;

    private final String desc;

    public static Optional<DataSourceStatusEnum> getInstance(Integer status) {
        if (Objects.isNull(status) || status < 0) {
            return Optional.empty();
        }

        for (DataSourceStatusEnum statusEnum : values()) {
            if (Objects.equals(status, statusEnum.getStatus())) {
                return Optional.of(statusEnum);
            }
        }

        return Optional.empty();
    }
}
