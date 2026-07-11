package booking;

import car.Car;
import car.CarService;
import exceptions.EmptyBookingException;
import exceptions.NoBookingFoundException;
import user.User;
import user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;
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

    private UUID readUUIDFromUser(String text) {
        UUID userInput = null;
        try {
            System.out.println(text);
            userInput = UUID.fromString(scanner.next());
        } catch (IllegalArgumentException e) {
            System.out.println("Enter a valid UUID");
            return readUUIDFromUser(text);
        }
        return userInput;
    }

    private String readString(String text) {
        String input = "";
        try {
            System.out.println(text);
            input = scanner.next();
        } catch (IllegalArgumentException e) {
            System.out.println("Enter valid String");
            return readString(text);
        }
        return input;
    }

    private LocalDate readDate(String text) {
        LocalDate inputDate;
        try {
            System.out.println(text);
            inputDate = LocalDate.parse(scanner.next());

        } catch (DateTimeParseException | IllegalArgumentException e) {
            return readDate(text);
        }
        return inputDate;
    }

    private boolean isValidBookingPeriod(LocalDate start, LocalDate end) {
        if (!start.isBefore(end)) {
            System.out.println("The start date cannot be in the past or after the end date.");
            return false;
        }
      return true;
    }

    private BigDecimal calculatePrice(LocalDate start, LocalDate end, BigDecimal rentalPricePerDay) {
        long between = ChronoUnit.DAYS.between(start, end);
        return rentalPricePerDay.multiply(BigDecimal.valueOf(between));
    }

    public void bookCar() {
        String regNumber = readString("Enter registration number");
        UUID userId = readUUIDFromUser("select user id");

        if (userExist(userId) && existingRegNumber(regNumber)) {
            LocalDate startDate, endDate;

            do {
                startDate = readDate("Enter start date");
                endDate = readDate("Enter endDate");
            } while (!isValidBookingPeriod(startDate, endDate));


            User user = userService.getUser(userId);
            Car car = carService.getCarByRegistrationNumber(regNumber);

            CarBooking booking = new CarBooking(
                    calculatePrice(startDate, endDate, car.getRentalPricePerDay()),
                    startDate,
                    endDate,
                    car,
                    user,
                    BookingStatus.ACTIVE);

            carBookingDao.addBooking(booking);
            System.out.println("Booking is created");
            System.out.println(booking);
        }
    }

    private boolean userExist(UUID userId) {
        return userService.checkUserExist(userId);
    }

    private boolean existingRegNumber(String regNumber) {
        return carService.regNumberExisting(regNumber);
    }

    public void deleteBooking() throws NoBookingFoundException {
        System.out.println("Put in Booking id");
        UUID bookingId = UUID.fromString(scanner.next());
        carBookingDao.deleteBooking(bookingId);
    }

    public void viewAllUserBookingCars() throws EmptyBookingException{
            for (User user : carBookingDao.getAllUserBookedCars())
                if (!Objects.isNull(user))
                    System.out.println(user);
    }

    public void viewAllBookings() throws EmptyBookingException{
            for (CarBooking booking : carBookingDao.getAllBookings())
                if (!Objects.isNull(booking))
                    System.out.println(booking);
    }

    public void viewAllAllAvailableCars() {
        Car[] allCars = carService.getAllCars();
        Car[] listAvailable = carBookingDao.getAllAvaiableCars(allCars);
        System.out.println(Arrays.toString(listAvailable));
    }

    public void viewAllAvailableElectroCars() {
        Car[] allCars = carService.getAllCars();
        Car[] listOfAvaible = carBookingDao.getAllAvaiableCars(allCars);
        for (Car car : listOfAvaible)
            if (car.isElectric())
                System.out.println(car);
    }

    public void viewAllUsers() {
        System.out.println(userService.getAllUsers());
    }
}
