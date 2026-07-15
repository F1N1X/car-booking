package booking;

import car.Car;
import car.CarService;
import exceptions.EmptyBookingException;
import exceptions.NoBookingFoundException;
import user.User;
import user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CarBookingService {

    private final CarService carService;
    private final UserService userService;
    private final CarBookingDao carBookingDao;

    public CarBookingService() {
        carService = new CarService();
        userService = new UserService();
        carBookingDao = new CarBookingDao();
    }

    private BigDecimal calculatePrice(LocalDate start, LocalDate end, BigDecimal rentalPricePerDay) {
        long between = ChronoUnit.DAYS.between(start, end);
        return rentalPricePerDay.multiply(BigDecimal.valueOf(between));
    }

    public void bookCar(UUID userId, String registerNumber, LocalDate startDate, LocalDate endDate) {

           if (!userExist(userId) || existingRegNumber(registerNumber))
               throw new IllegalArgumentException("user or register number not exist");

            User user = userService.getUserByID(userId);
            Car car = carService.getCarByRegistrationNumber(registerNumber);

            CarBooking booking = new CarBooking(
                    calculatePrice(startDate, endDate, car.getRentalPricePerDay()),
                    startDate,
                    endDate,
                    car,
                    user,
                    BookingStatus.ACTIVE);

            carBookingDao.addBooking(booking);
        }


    private boolean userExist(UUID userId) {
        return userService.checkUserExist(userId);
    }

    private boolean existingRegNumber(String regNumber) {
        return carService.regNumberExisting(regNumber);
    }

    public void deleteBooking(UUID bookingId) throws NoBookingFoundException {
        carBookingDao.deleteBooking(bookingId);
    }

    public User[] viewAllUserBookingCars() throws EmptyBookingException{
            return carBookingDao.getAllUserBookedCars();
    }

    public CarBooking[] viewAllBookings() throws EmptyBookingException{
        return carBookingDao.getAllBookings();
    }

    public Car[] viewAllAllAvailableCars() {
        Car[] allCars = carService.getAllCars();
        return carBookingDao.getAllBookedCars(allCars);
    }

    public Car[] viewAllAvailableElectricCars() {
        Car[] allCars = carService.getAllCars();
        return carBookingDao.getAllBookedCars(allCars);
    }

    public User[] viewAllUsers() {
        return userService.getAllUsers();
    }
}
