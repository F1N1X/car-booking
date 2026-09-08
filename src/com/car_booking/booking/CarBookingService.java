package booking;

import car.Car;
import car.CarService;
import exceptions.*;
import user.User;
import user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarBookingService {

    private final CarService carService;
    private final UserService userService;
    private final CarBookingDao carBookingDao;

    public CarBookingService(CarService carService, UserService userService, CarBookingDao carBookingDao) {
        this.carService = carService;
        this.userService = userService;
        this.carBookingDao = carBookingDao;
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

            carBookingDao.saveBooking(booking);
        }

    private static boolean isValidBookingPeriod(LocalDate start, LocalDate end) {
        if (start.isBefore(LocalDate.now()) || end.isBefore(LocalDate.now()))
            return false;

        return start.isBefore(end);
    }

    public void deleteBooking(UUID bookingId){
        carBookingDao.deleteBooking(bookingId);
    }

    public List<User> viewAllUsersWithBookings(){
        List<CarBooking> bookings = carBookingDao.getBookings();

        if (bookings.isEmpty())
            throw new NoUserBookedCarException("No user booked cars");

        return bookings.stream()
                .map(CarBooking::getUser)
                .toList();
    }

    public List<CarBooking> viewAllBookings() {
        List<CarBooking> allBookings = carBookingDao.getBookings();
        if (allBookings.isEmpty())
            throw new NoBookingFoundException("no booking available");
        return allBookings;
    }

    public List<Car> viewAllAvailableCars() {
        List<Car> allCars = carService.getAllCars();
        if (allCars.isEmpty())
            throw new NoCarFoundException("no cars available");

        List<Car> availableCars = filterAvailableCars(allCars, false);

        if (availableCars.isEmpty())
            throw new NoAvailableCarsException("no cars available for booking");
        return availableCars;
    }

    public List<Car> viewAllAvailableElectricCars() {
        List<Car> allCars = carService.getAllCars();
        if (allCars.isEmpty())
            throw new NoCarFoundException("no cars available");

        List<Car> availableCars = filterAvailableCars(allCars, true);

        if (availableCars.isEmpty())
            throw new NoAvailableCarsException("no cars available for booking");
        return availableCars;
    }

    public List<User> viewAllUsers() {
        return userService.getAllUsers();
    }

    private List<Car> filterAvailableCars(List<Car> cars, boolean electricOnly) {
        return cars.stream()
                .filter(car -> !carBookingDao.isCarBooked(car))
                .filter(car -> !electricOnly || car.isElectric())
                .toList();
    }
}
