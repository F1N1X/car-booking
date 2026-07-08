package exceptions;

public class NoCapacityException extends RuntimeException {
    public NoCapacityException(String message) {
        super(message);
    }
}
