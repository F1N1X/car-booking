package car;

import java.util.Optional;
import java.util.UUID;

public interface CarDao {
    Car[] getCars();
    Car findCarById(UUID id);

    Optional<Car> getCarByRegistrationNumber(String number);
}
