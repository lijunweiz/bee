package cn.unminded.bee.core.datasource;

import cn.unminded.bee.core.exception.BeeCoreException;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 从数据库中获取变量
 * @author lijunwei
 */
public class DatasourceManager {
    private static final Logger logger = LoggerFactory.getLogger(DatasourceManager.class);

    private final Pattern sqlParser = Pattern.compile("#\\{(.*?)}");

    /**
     * 数据源池, key为数据源名称, value为数据源连接池
     */
    private Map<String, DruidDataSource> databaseDataSource;

    /**
     * 远程数据源
     */
    private Map<String, RuleDataSource<RemoteDataInput, Map<String, Object>>> remoteDataSource;

    public Map<String, DruidDataSource> getDatabaseDataSource() {
        return databaseDataSource;
    }

    public DatasourceManager setDatabaseDataSource(Map<String, DruidDataSource> databaseDataSource) {
        this.databaseDataSource = databaseDataSource;
        return this;
    }

    public Map<String, RuleDataSource<RemoteDataInput, Map<String, Object>>> getRemoteDataSource() {
        return remoteDataSource;
    }

    public DatasourceManager setRemoteDataSource(Map<String, RuleDataSource<RemoteDataInput, Map<String, Object>>> remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
        return this;
    }

    public Map<String, Object> getMap(DatabaseInput input) {
        try {
            return this.executeQuery(input);
        } catch (Exception e) {
            throw new BeeCoreException(e);
        }
    }

    public Map<String, Object> getMap(RemoteDataInput input) {
        try {
            RuleDataSource<RemoteDataInput, Map<String, Object>> dataSource = this.remoteDataSource.get(input.getDatasourceName());
            if (Objects.isNull(dataSource)) {
                logger.error("未获取到数据源: {}", input.getDatasourceName());
                return Collections.emptyMap();
            }
            return dataSource.execute(input);
        } catch (Exception e) {
            throw new BeeCoreException(e);
        }
    }

    private Connection getConnection(String dbName) throws SQLException {
        return databaseDataSource.get(dbName).getConnection();
    }

    private Map<String, Object> executeQuery(DatabaseInput input) throws SQLException {
        String sql = this.handleSql(input.getSql(), input.getArgs());
        if (logger.isDebugEnabled()) {
            logger.debug("当前数据库: {}, 解析后sql: {}", input.getDbName(), sql);
        }
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.getConnection(input.getDbName());
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            return this.collectToMap(resultSet);
        } finally {
            this.close(connection, statement, resultSet);
        }
    }

    /**
     * 替换sql占位符为对应值
     * @param sql 含有占位符的sql
     * @param args 参数值
     * @return 解析完毕的sql
     */
    private String handleSql(String sql, Map<String, Object> args) {
        Matcher matcher = sqlParser.matcher(sql);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            Object arg = args.get(matcher.group(1));
            String str = arg.toString();
            if (arg instanceof Collection) {
                Object o = ((Collection<?>) arg).toArray()[0];
                if (o instanceof Character || o instanceof CharSequence) {
                    List<String> collect = ((Collection<?>) arg).stream().map(x -> "'" + x + "'").collect(Collectors.toList());
                    str = collect.toString();
                }
                matcher.appendReplacement(sb, str.substring(1, str.length() - 1));
            } else if (arg instanceof Character || arg instanceof CharSequence){
                matcher.appendReplacement(sb, "'" + str + "'");
            } else {
                matcher.appendReplacement(sb, str);
            }
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    private List<Object> collectToList(ResultSet resultSet) throws SQLException {
        List<Object> list = Lists.newArrayList();
        while (resultSet.next()) {
            list.add(resultSet.getObject(1));
        }

        return list;
    }

    /**
     * 收集结果为{@link Map}, value需为基础类型
     * @param resultSet sql结果集
     * @return 结果map
     * @throws SQLException
     */
    private Map<String, Object> collectToMap(ResultSet resultSet) throws SQLException {
        Map<String, Object> resultMap = Maps.newHashMap();
        while (resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                resultMap.put(metaData.getColumnLabel(i), resultSet.getObject(i));
            }
        }

        return resultMap;
    }

    private List<Map<String, Object>> collectToListMap(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> list = Lists.newArrayList();
        while (resultSet.next()) {
            Map<String, Object> resultMap = Maps.newHashMap();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                resultMap.put(metaData.getColumnLabel(i), resultSet.getObject(i));
            }
            list.add(resultMap);
        }

        return list;
    }

    private Object collectToObject(ResultSet resultSet) throws SQLException {
        List<Object> list = this.collectToList(resultSet);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    private void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.warn("数据库连接关闭失败", e);
        }
    }

}
