package exceptions;

public class NoBookingFoundException extends RuntimeException {
    public NoBookingFoundException(String message) {
        super(message);
    }
}
