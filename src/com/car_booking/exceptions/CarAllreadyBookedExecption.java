package exceptions;

public class CarAllreadyBookedExecption extends RuntimeException {
    public CarAllreadyBookedExecption(String message) {
        super(message);
    }
}
