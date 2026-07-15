package booking;

import car.Car;
import car.CarService;
import exceptions.*;
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

            if (!isValidBookingPeriod(startDate, endDate)) throw new InvalidBookingPeriodException("invalid booking period");

            User user = userService.getUserByID(userId)
                    .orElseThrow( () -> new NoUserFoundException("No user found with id: " + userId));
            Car car = carService.getCarByRegistrationNumber(registerNumber)
                    .orElseThrow( () -> new NoCarFoundException("No car found with registration-number: " + registerNumber));

            if (carBookingDao.isCarBooked(car)) throw new CarAlreadyBookedException("Car is not available");

            CarBooking booking = new CarBooking(
                    calculatePrice(startDate, endDate, car.getRentalPricePerDay()),
                    startDate,
                    endDate,
                    car,
                    user,
                    BookingStatus.ACTIVE);

            carBookingDao.addBooking(booking);
        }

    private static boolean isValidBookingPeriod(LocalDate start, LocalDate end) {
        if (start.isBefore(LocalDate.now()) || end.isBefore(LocalDate.now()))
            return false;

        return start.isBefore(end);
    }

    public void deleteBooking(UUID bookingId){
        if (!carBookingDao.deleteBooking(bookingId))
            throw new NoBookingFoundException("no booking found with id: " + bookingId);
    }

    public User[] viewAllUserBookingCars(){
        User[] allUserBookedCars = carBookingDao.getAllUserBookedCars();
        if (allUserBookedCars.length == 0)
            throw new NoUserBookedCarException("No user booked cars");
        return allUserBookedCars;
    }

    public CarBooking[] viewAllBookings() {
        CarBooking[] allBookings = carBookingDao.getAllBookings();
        if (allBookings.length == 0)
            throw new NoBookingFoundException("no booking available");
        return allBookings;
    }

    public Car[] viewAllAvailableCars() {
        Car[] allCars = carService.getAllCars();
        if (allCars.length == 0)
            throw new NoCarFoundException("no cars available");

        Car[] availableCars = filterAvailableCars(allCars, false);

        if (availableCars.length == 0)
            throw new NoAvaibleCarsException("no cars available for booking");
        return availableCars;
    }

    public Car[] viewAllAvailableElectricCars() {
        Car[] allCars = carService.getAllCars();
        if (allCars.length == 0)
            throw new NoCarFoundException("no cars available");

        Car[] availableCars = filterAvailableCars(allCars, true);

        if (availableCars.length == 0)
            throw new NoAvaibleCarsException("no cars available for booking");
        return availableCars;
    }

    public User[] viewAllUsers() {
        return userService.getAllUsers();
    }

    private Car[] filterAvailableCars(Car[] cars, boolean electricOnly) {
        int count = 0;

        for (Car car : cars) {
            if (carBookingDao.isCarBooked(car)) {
                continue;
            }

            if (electricOnly && !car.isElectric()) {
                continue;
            }

            count++;
        }

        Car[] result = new Car[count];
        int index = 0;

        for (Car car : cars) {
            if (carBookingDao.isCarBooked(car)) {
                continue;
            }

            if (electricOnly && !car.isElectric()) {
                continue;
            }

            result[index++] = car;
        }

        return result;
    }
}
