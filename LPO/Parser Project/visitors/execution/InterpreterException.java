package progetto2023_lpo22_37.visitors.execution;

public class InterpreterException extends RuntimeException {

    public InterpreterException() {
    }

    public InterpreterException(String message, Throwable cause, boolean enableSuppression, boolean wirtableStackTrace) {
        super(message, cause, enableSuppression, wirtableStackTrace);
    }

    public InterpreterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterpreterException(String message) {
        super(message);
    }

    public InterpreterException(Throwable cause) {
        super(cause);
    }
    
}
