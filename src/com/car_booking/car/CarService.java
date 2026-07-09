package car;

import java.util.UUID;

public class CarService {

    private final CarDAO carDao;

    public CarService() {
        carDao = new CarDAO();
    }


    public Car getCar(UUID carID) {
        return carDao.getCarById(carID);
    }

    public Car getCarByRegistrationNumber(String number) {
        return carDao.getCarByRegistrationNumber(number);
    }

    public Car[] getAllCars() {
        return carDao.getAllCars();
    }
}
