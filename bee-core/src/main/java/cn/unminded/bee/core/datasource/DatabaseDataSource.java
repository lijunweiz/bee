package cn.unminded.bee.core.datasource;

import java.util.Map;

/**
 * 从数据库中获取变量只有这一个实现即可
 * @author lijunwei
 */
public class DatabaseDataSource implements RuleDataSource<DatabaseInput, Map<String, Object>> {

    private DatasourceManager datasourceManager;

    public void setDatasourceManager(DatasourceManager datasourceManager) {
        this.datasourceManager = datasourceManager;
    }

    @Override
    public Map<String, Object> run(DatabaseInput input) {
        return datasourceManager.getMap(input);
    }

}
