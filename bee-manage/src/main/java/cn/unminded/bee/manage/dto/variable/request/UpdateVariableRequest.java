package cn.unminded.bee.manage.dto.variable.request;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

/**
 * @author lijunwei
 */
@Data
public class UpdateVariableRequest {

    /**
     * 变量英文名称
     */
    @NotBlank(message = "变量英文名称不能为空")
    private String variableNameEn;

    /**
     * 变量描述，如变量含义或变量的使用
     */
    private String variableDesc;

    /**
     * 变量来源，如来自哪个系统，内部或外部，此处指系统id或名称
     */
    private String variableSource;

    /**
     * 变量类型，比如复贷授信
     */
    private String variableType;

    /**
     * 变量获取地址，如: http://127.0.0.1/v/get/v1
     */
    private String variableAddress;

    /**
     * 变量作者
     */
    @NotBlank(message = "变量作者不能为空")
    private String author;

    /**
     * 调用变量获取地址时的入参，默认json
     */
    private String variableParam;

    /**
     * 需求名称，改变量是哪个需求提出的，以供后续查询
     */
    private String requirementName;

    private Integer variableStatus;

    private Integer variableVersion;

}
