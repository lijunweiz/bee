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
public enum VariableStatusEnum {

    FRESH(0, "新建"),
    EDITING(1, "编辑中"),
    DRAFT(2, "草稿"),
    SUBMIT_REVIEW(3, "提交审核"),
    UNDER_REVIEW(4, "审核中"),
    REVIEW_PASS(5, "审核通过"),
    REVIEW_REJECT(6, "审核拒绝"),
    START_PUBLISH(7, "开始发布"),
    PUBLISH_SUCCESS(8, "发布成功|运行中"),
    PUBLISH_FAIL(9, "发布失败"),
    START_DELETE(10, "准备删除"),
    DELETE_SUCCESS(11, "删除成功|已下线"),
    DELETE_FAIL(12, "删除失败")

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
}
