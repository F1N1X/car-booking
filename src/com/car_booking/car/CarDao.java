package car;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarDao {
    List<Car> getCars();
    Car findCarById(UUID id);
    Optional<Car> getCarByRegistrationNumber(String number);
}
