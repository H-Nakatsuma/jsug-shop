package jsug.component.exception;

public class EmptyCartOrderException extends RuntimeException {
    public EmptyCartOrderException(String message) {
        super(message);
    }
}
