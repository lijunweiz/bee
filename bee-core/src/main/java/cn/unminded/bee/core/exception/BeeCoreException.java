package cn.unminded.bee.core.exception;

/**
 * @author lijunwei
 */
public class BeeCoreException extends RuntimeException {
    public BeeCoreException() {
    }

    public BeeCoreException(String message) {
        super(message);
    }

    public BeeCoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeeCoreException(Throwable cause) {
        super(cause);
    }

    public BeeCoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
