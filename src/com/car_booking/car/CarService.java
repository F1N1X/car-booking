package car;


import java.util.Optional;

public class CarService {

    private final CarDAO carDao;

    public CarService() {
        carDao = new StaticCarDAO();
    }

    public Optional<Car> getCarByRegistrationNumber(String number) {
        return carDao.getCarByRegistrationNumber(number);
    }

    public Car[] getAllCars() {
        return carDao.getAllCars();
    }
}
