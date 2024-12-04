package cn.unminded.bee.common.constant;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 字典状态枚举
 * @author lijunwei
 */
@Getter
@AllArgsConstructor
public enum DictStatusEnum {
    STOP(0, "停用"),
    RUNNING(1, "启用")
    ;

    private final Integer status;

    private final String desc;

    public static Optional<DictStatusEnum> getInstance(Integer status) {
        if (Objects.isNull(status) || status < 0) {
            return Optional.empty();
        }

        for (DictStatusEnum statusEnum : values()) {
            if (Objects.equals(status, statusEnum.getStatus())) {
                return Optional.of(statusEnum);
            }
        }

        return Optional.empty();
    }

    public static Map<Integer, String> toMap() {
        return Arrays.stream(values())
                .collect(Collectors.toMap(DictStatusEnum::getStatus, DictStatusEnum::getDesc));
    }

    public static List<DictStatusEnum> toList() {
        return Arrays.asList(values());
    }

    public static List<Map<String, Object>> toMapList() {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (DictStatusEnum statusEnum : values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", statusEnum.status);
            map.put("desc", statusEnum.desc);
            list.add(map);
        }

        return list;
    }
}