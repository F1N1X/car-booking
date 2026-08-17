package car;


import java.util.List;
import java.util.Optional;

public class CarService {

    private final CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }


    public Optional<Car> getCarByRegistrationNumber(String number) {
        return carDao.getCarByRegistrationNumber(number);
    }

    public List<Car> getAllCars() {
        return carDao.getCars();
    }
}
