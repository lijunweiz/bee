package cn.unminded.bee.manage.dto.variable.request;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

/**
 * 变量管理；新增变量入参
 *
 * @author lijunwei
 */
@Data
public class AddVariableRequest {

    /**
     * 变量英文名称
     */
    @NotBlank(message = "变量英文名称不能为空")
    private String variableNameEn;

    /**
     * 变量中文名称
     */
    @NotBlank(message = "变量中文名称不能为空")
    private String variableNameZh;

    /**
     * 变量描述，如变量含义或变量的使用
     */
    @NotBlank(message = "变量描述不能为空")
    private String variableDesc;

    /**
     * 变量来源，如来自哪个系统，内部或外部，此处指系统id或名称
     */
    @NotBlank(message = "变量来源不能为空")
    private String variableSource;

    /**
     * 变量类型，比如复贷授信
     */
    @Nullable
    private String variableType;

    /**
     * 变量获取地址，如: http://127.0.0.1/v/get/v1
     */
    @NotBlank(message = "变量获取地址不能为空")
    private String variableAddress;

    /**
     * 变量作者
     */
    @NotBlank(message = "变量作者不能为空")
    private String author;

    /**
     * 调用变量获取地址时的入参，默认json
     */
    @Nullable
    private String variableParam;

    /**
     * 需求名称，改变量是哪个需求提出的，以供后续查询
     */
    @NotBlank(message = "需求名称不能为空")
    private String requirementName;

}
