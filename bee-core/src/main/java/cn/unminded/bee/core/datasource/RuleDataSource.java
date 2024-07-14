package cn.unminded.bee.core.datasource;

/**
 * @author lijunwei
 */
public interface RuleDataSource<T, R> {

    R run(T input);

}
