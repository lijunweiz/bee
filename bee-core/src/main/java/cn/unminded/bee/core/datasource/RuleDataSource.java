package cn.unminded.bee.core.datasource;

/**
 * 执行引擎的数据来源
 * @author lijunwei
 */
public interface RuleDataSource<T, R> {

    /**
     * 可以对参数进行一些预处理
     * @param input 输入内容
     * @return true 处理成功 or false 失败
     */
    default void preOperator(T input) {
    }

    /**
     * 用户需要必须实现基本逻辑
     * @param input 输入内容
     * @return
     */
    R run(T input);

    /**
     * 执行引擎会调用的方法
     * @param input 输入内容
     * @return
     */
    default R execute(T input) {
        this.preOperator(input);
        R r = this.run(input);
        this.postOperator(input, r);
        return r;
    }

    /**
     * 处理输出结果
     * @param input 输入内容
     * @param result 输出结果
     * @param <T> 输入类型
     * @param <R> 输出类型
     */
    default <T, R> void postOperator(T input, R result) {
    }

}
