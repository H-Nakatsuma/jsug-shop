package jsug.component.exception;

public class InvalidCartOrderException extends RuntimeException {
    public InvalidCartOrderException(String message) {
        super(message);
    }
}
