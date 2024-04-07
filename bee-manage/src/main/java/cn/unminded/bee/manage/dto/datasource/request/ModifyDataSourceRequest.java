package cn.unminded.bee.manage.dto.datasource.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lijunwei
 */
@Data
public class ModifyDataSourceRequest {

    @NotBlank(message = "数据源id不能为空")
    private Long dataSourceId;

    /**
     * 数据源名称
     */
    private String dataSourceName;

    /**
     * 数据源类型
     */
    private String dataSourceType;

    /**
     * 数据源状态
     */
    private Integer status;
}
