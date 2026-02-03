package cn.unminded.bee.core.util;

import cn.unminded.bee.core.exception.BeeCoreException;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author lijunwei
 */
public class BeeCoreExceptionUtil {

    private BeeCoreExceptionUtil() {
        throw new UnsupportedOperationException();
    }

    public static BeeCoreException build(Throwable cause) {
        return new BeeCoreException(cause);
    }

    public static BeeCoreException build(String message) {
        return new BeeCoreException(message);
    }

    public static BeeCoreException build(String message, Throwable cause) {
        return new BeeCoreException(message, cause);
    }

    public static void nullOrEmptyToThrow(Object obj, String message) {
        if (ObjectUtils.isEmpty(obj)) {
            throw new BeeCoreException(message);
        }
    }

    public static void trueToThrow(boolean condition, String message) {
        if (condition) {
            throw new BeeCoreException(message);
        }
    }

}
