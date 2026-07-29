package car;


import java.util.Optional;

public class CarService {

    private final CarArrayDataAccessService carDao;

    public CarService() {
        carDao = new CarArrayDataAccessService();
    }

    public Optional<Car> getCarByRegistrationNumber(String number) {
        return carDao.getCarByRegistrationNumber(number);
    }

    public Car[] getAllCars() {
        return carDao.getCars();
    }
}
