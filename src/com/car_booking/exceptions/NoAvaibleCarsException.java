package exceptions;

public class NoAvaibleCarsException extends RuntimeException {
    public NoAvaibleCarsException(String message) {
        super(message);
    }
}
