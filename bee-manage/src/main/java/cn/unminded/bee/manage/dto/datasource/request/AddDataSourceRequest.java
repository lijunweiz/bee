package cn.unminded.bee.manage.dto.datasource.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lijunwei
 */
@Data
public class AddDataSourceRequest {

    /**
     * 数据源名称
     */
    @NotBlank(message = "数据源名称不能为空")
    private String dataSourceName;

    /**
     * 数据源类型
     */
    @NotBlank(message = "数据源类型不能为空")
    private String dataSourceType;

    /**
     * 数据源状态
     */
    private Integer status;

    /**
     * 描述信息
     */
    private String desc;

    /**
     * 通讯协议
     */
    private String protocol;

    /**
     * 调用http or https方法
     */
    private String method;

    /**
     * 调用地址ip:port/aa/bb
     */
    private String address;

    /**
     * a=b&d=e
     */
    private String queryParam;

    /**
     * 请求头, 多个逗号分割
     */
    private String headers;

    /**
     * 请求体，post需要该参数
     */
    private String requestBody;

    /**
     * 从响应体中截取的参数 多个逗号分割
     */
    private String extractVariable;


}
