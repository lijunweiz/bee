package cn.unminded.bee.common.exception;

/**
 * @author lijunwei
 */
public class VariableManageException extends RuntimeException {
    public VariableManageException() {
    }

    public VariableManageException(String message) {
        super(message);
    }

    public VariableManageException(String message, Throwable cause) {
        super(message, cause);
    }

    public VariableManageException(Throwable cause) {
        super(cause);
    }

    public VariableManageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
