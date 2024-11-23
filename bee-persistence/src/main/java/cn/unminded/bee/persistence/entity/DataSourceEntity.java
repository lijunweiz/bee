package cn.unminded.bee.persistence.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 数据源配置
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class DataSourceEntity {

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
    private Integer dataSourceStatus;

    /**
     * 描述信息
     */
    private String dataSourceDesc;

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
     * 从响应体中截取的变量 多个逗号分割
     */
    private String extractVariable;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
