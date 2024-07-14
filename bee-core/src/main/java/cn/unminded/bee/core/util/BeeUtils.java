package cn.unminded.bee.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * @author lijunwei
 */
public class BeeUtils {

    private BeeUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 创建一个新的实例
     * @param clazz 模块class
     * @param <T> class类型
     * @return 一个实例
     */
    public static <T> Object newInstance(Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz can not be null");
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    /**
     * 通过java反射获取某个成员变量值
     * @param target 含有某个变量值的对象实例
     * @param fieldName 变量名称
     * @return 变量值, 当没有对应变量或访问失败返回null
     */
    public static Object getValue(Object target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            if (!field.canAccess(target)) {
                field.setAccessible(true);
            }
            return field.get(fieldName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

}
