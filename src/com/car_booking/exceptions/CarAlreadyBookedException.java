package exceptions;

public class CarAlreadyBookedException extends RuntimeException {
    public CarAlreadyBookedException(String message) {
        super(message);
    }
}
