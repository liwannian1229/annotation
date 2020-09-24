package exception;

public class NoAuthException extends AnnotationException {

    public NoAuthException(String message) {
        super(message);
    }

    public NoAuthException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
