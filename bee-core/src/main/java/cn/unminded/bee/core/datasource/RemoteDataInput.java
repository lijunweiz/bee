package cn.unminded.bee.core.datasource;

import java.util.Map;

/**
 * @author lijunwei
 */
public class RemoteDataInput {

    private String datasourceName;

    private String datasourceType;

    private String url;

    private String method;

    private Map<String, Object> param;

    private Map<String, Object> headers;

    private Object body;

    public String getDatasourceName() {
        return datasourceName;
    }

    public RemoteDataInput setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
        return this;
    }

    public String getDatasourceType() {
        return datasourceType;
    }

    public RemoteDataInput setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public RemoteDataInput setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public RemoteDataInput setMethod(String method) {
        this.method = method;
        return this;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public RemoteDataInput setParam(Map<String, Object> param) {
        this.param = param;
        return this;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public RemoteDataInput setHeaders(Map<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    public Object getBody() {
        return body;
    }

    public RemoteDataInput setBody(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        return "RemoteDataInput{" +
                "datasourceName='" + datasourceName + '\'' +
                ", datasourceType='" + datasourceType + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", param=" + param +
                ", headers=" + headers +
                ", body=" + body +
                '}';
    }
}
