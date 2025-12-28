package cn.unminded.bee.common.util;

import cn.unminded.bee.common.exception.BeeException;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author lijunwei
 */
public class BeeExceptionUtil {

    private BeeExceptionUtil() {
        throw new UnsupportedOperationException();
    }

    public static BeeException build(String message) {
        return new BeeException(message);
    }

    public static BeeException build(String message, Throwable cause) {
        return new BeeException(message, cause);
    }

    public static void nullOrEmptyToThrow(Object obj, String message) {
        if (ObjectUtils.isEmpty(obj)) {
            throw new BeeException(message);
        }
    }


}
