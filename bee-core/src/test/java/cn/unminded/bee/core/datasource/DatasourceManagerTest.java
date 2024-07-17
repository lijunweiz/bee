package cn.unminded.bee.core.datasource;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mysql.cj.jdbc.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DatasourceManagerTest {

    DatabaseDataSource databaseDataSource = new DatabaseDataSource();

    @BeforeEach
    void setUp() throws SQLException {
        DatasourceManager datasourceManager = new DatasourceManager();
        DruidDataSource beeDatasource = new DruidDataSource(false);
        beeDatasource.setUrl("jdbc:mysql://localhost:3306/bee?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Shanghai");
        beeDatasource.setUsername("root");
        beeDatasource.setPassword("123456");
        beeDatasource.setDriverClassName(Driver.class.getName());
        beeDatasource.setProxyFilters(Lists.newArrayList(new Slf4jLogFilter()));
        beeDatasource.init();

        Map<String, DruidDataSource> dataSource = Maps.newHashMap();
        dataSource.put("bee", beeDatasource);
        datasourceManager.setDatabaseDataSource(dataSource);
        databaseDataSource.setDatasourceManager(datasourceManager);
    }

    @Test
    void getMap() {
        String sql = "select variableId, variableNameEn, variableStatus from t_variable where variableStatus = #{variableStatus} and author = #{author} and variableId in (#{list}) limit 1";
        Map<String, Object> args = Maps.newHashMap();
        args.put("variableStatus", 2);
        args.put("author", "admin");
        args.put("list", Lists.newArrayList("10000000", "10000001", "10000002", "10000003"));

        DatabaseInput input = new DatabaseInput().setDbType("mysql").setDbName("bee").setSql(sql).setArgs(args);
        Map<String, Object> map = databaseDataSource.execute(input);
        System.err.println(JSON.toJSON(map));

        map = databaseDataSource.execute(input);
        System.err.println(JSON.toJSON(map));
        assertFalse(map.isEmpty());
    }

}