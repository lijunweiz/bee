package cn.unminded.bee.core;

import java.util.Map;
import java.util.Objects;

/**
 * @author lijunwei
 */
public class RuleContext {

    /**
     * 唯一标志
     */
    private String contextId;

    /**
     * 唯一标志
     */
    private String contextName;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 参与决策的数据, 读写
     */
    private Map<String, Object> data;

    public String getContextId() {
        return contextId;
    }

    public RuleContext setContextId(String contextId) {
        this.contextId = contextId;
        return this;
    }

    public String getContextName() {
        return contextName;
    }

    public RuleContext setContextName(String contextName) {
        this.contextName = contextName;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public RuleContext setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public RuleContext setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleContext that = (RuleContext) o;
        return Objects.equals(contextId, that.contextId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contextId);
    }

    @Override
    public String toString() {
        return "RuleContext{" +
                "contextId='" + contextId + '\'' +
                ", contextName='" + contextName + '\'' +
                ", userId='" + userId + '\'' +
                ", data=" + data +
                '}';
    }
}
