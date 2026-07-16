package car;

import java.util.Optional;

public interface CarDAO {
    Optional<Car> getCarByRegistrationNumber(String number);
    Car[] getAllCars();
}
