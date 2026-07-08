package booking;

import car.Car;
import car.CarService;
import user.User;
import user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.UUID;

public class CarBookingService {

    private final CarService carService;
    private final UserService userService;
    private final CarBookingDao carBookingDao;
    private final Scanner scanner;

    public CarBookingService() {
        carService = new CarService();
        userService = new UserService();
        scanner = new Scanner(System.in);
        carBookingDao = new CarBookingDao();
    }


    public void bookCar() {
        System.out.println("select car reg number");
        String regNumber = scanner.nextLine();
        System.out.println("select user id");
        UUID userId = UUID.fromString(scanner.next());
        System.out.println("select start date");
        LocalDate startDate = LocalDate.parse(scanner.next());
        System.out.println("select start date");
        LocalDate endDate = LocalDate.parse(scanner.next());
        LocalDateTime bookedAt = LocalDateTime.now();
        System.out.println("select car id");
        UUID carId = UUID.fromString(scanner.next());

        User user = userService.getUser(userId);
        Car car = carService.getCarByRegistrationNumber(regNumber);

        long between = ChronoUnit.DAYS.between(startDate, endDate);
        BigDecimal price = car.getRentalPricePerDay().multiply(BigDecimal.valueOf(between));

        CarBooking booking = new CarBooking(
                price,
                bookedAt,
                startDate,
                endDate,
                car,
                user,
                BookingStatus.ACTIVE);

        carBookingDao.addBooking(booking);
    }

    public void deleteBooking() {
    }

    public void viewAllUserBookingCars() {
    }

    public void viewAllBookings() {
    }

    public void viewAllAvaibleCars() {
    }

    public void viewAllAvaibleElectricCars() {
    }

    public void viewAllUsers() {
    }
}
