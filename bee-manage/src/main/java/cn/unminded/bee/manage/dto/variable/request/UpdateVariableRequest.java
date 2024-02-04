package cn.unminded.bee.manage.dto.variable.request;

import lombok.Data;

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
     * 数据源名称
     */
    @NotBlank(message = "数据源名称不能为空")
    private String dataSourceName;

    /**
     * 数据源类型，比如信用中国
     */
    @NotBlank(message = "数据源类型不能为空")
    private String dataSourceType;

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
