package cn.unminded.bee.core.datasource;

import java.util.Map;
import java.util.Objects;

/**
 * 从数据库中获取变量
 * @author lijunwei
 */
public class DatasourceManager {

    /**
     * 数据库数据源
     */
    private DatabaseDataSource databaseDataSource;

    /**
     * 远程数据源
     */
    private Map<String, RuleDataSource<RemoteDataInput, Map<String, Object>>> remoteDataSource;

    public DatabaseDataSource getDatabaseDataSource() {
        return databaseDataSource;
    }

    public void setDatabaseDataSource(DatabaseDataSource databaseDataSource) {
        this.databaseDataSource = databaseDataSource;
    }

    public Map<String, RuleDataSource<RemoteDataInput, Map<String, Object>>> getRemoteDataSource() {
        return remoteDataSource;
    }

    public DatasourceManager setRemoteDataSource(Map<String, RuleDataSource<RemoteDataInput, Map<String, Object>>> remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
        return this;
    }

    public Map<String, Object> getMap(DatabaseInput input) {
        Objects.requireNonNull(this.databaseDataSource.getDataSource().get(input.getDbName()), input.getDbName() + "数据源不能为空");
        return databaseDataSource.execute(input);
    }

    public Map<String, Object> getMap(RemoteDataInput input) {
        RuleDataSource<RemoteDataInput, Map<String, Object>> dataSource = this.remoteDataSource.get(input.getDatasourceName());
        Objects.requireNonNull(dataSource, input.getDatasourceName() + "数据源不能为空");
        return dataSource.execute(input);
    }

}
