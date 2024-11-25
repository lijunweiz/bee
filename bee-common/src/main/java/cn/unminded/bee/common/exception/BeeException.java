package cn.unminded.bee.common.exception;

/**
 * @author lijunwei
 */
public class BeeException extends RuntimeException {
    public BeeException() {
    }

    public BeeException(String message) {
        super(message);
    }

    public BeeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeeException(Throwable cause) {
        super(cause);
    }

    public BeeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
