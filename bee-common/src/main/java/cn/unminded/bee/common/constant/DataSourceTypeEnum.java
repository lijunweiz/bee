package cn.unminded.bee.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lijunwei
 */
@Getter
@AllArgsConstructor
public enum DataSourceTypeEnum {

    BIG_DATA("大数据", "大数据"),
    CREDIT_CHINA("信用中国", "信用中国"),
    BAI_HANG_CREDIT("百行征信", "百行征信"),
    CREDIT_MANAGE("征信系统", "征信系统"),
    DIAN_HUA("电话帮", "电话帮"),

    ;

    private final String name;

    private final String desc;

    public static List<String> getDataSourceTypeNames() {
        return Stream.of(values()).map(x -> x.name).collect(Collectors.toList());
    }

    public static Optional<DataSourceTypeEnum> getInstance(String type) {
        if (StringUtils.isBlank(type)) {
            return Optional.empty();
        }

        for (DataSourceTypeEnum typeEnum : values()) {
            if (Objects.equals(type, typeEnum.getName())) {
                return Optional.of(typeEnum);
            }
        }

        return Optional.empty();
    }

}
