package cn.unminded.bee.core.datasource;

import java.util.Map;

/**
 * @author lijunwei
 */
public class DatabaseInput {

    private String dbName;

    private String dbType;

    private String sql;

    private Map<String, Object> args;

    public String getDbName() {
        return dbName;
    }

    public DatabaseInput setDbName(String dbName) {
        this.dbName = dbName;
        return this;
    }

    public String getDbType() {
        return dbType;
    }

    public DatabaseInput setDbType(String dbType) {
        this.dbType = dbType;
        return this;
    }

    public String getSql() {
        return sql;
    }

    public DatabaseInput setSql(String sql) {
        this.sql = sql;
        return this;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public DatabaseInput setArgs(Map<String, Object> args) {
        this.args = args;
        return this;
    }

    @Override
    public String toString() {
        return "DatabaseProperties{" +
                "dbName='" + dbName + '\'' +
                ", dbType='" + dbType + '\'' +
                ", sql='" + sql + '\'' +
                ", args='" + args + '\'' +
                '}';
    }
}
