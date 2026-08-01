package car;


import java.util.Optional;

public class CarService {

    private final CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }


    public Optional<Car> getCarByRegistrationNumber(String number) {
        return carDao.getCarByRegistrationNumber(number);
    }

    public Car[] getAllCars() {
        return carDao.getCars();
    }
}
