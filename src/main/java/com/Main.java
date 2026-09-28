package com;

import com.booking.CarBooking;
import com.booking.CarBookingArrayDataAccessService;
import com.booking.CarBookingDao;
import com.booking.CarBookingService;
import com.car.Car;
import com.car.CarArrayDataAccessService;
import com.car.CarDao;
import com.car.CarService;
import com.github.javafaker.Faker;
import com.user.User;
import com.user.UserArrayDataAccessService;
import com.user.UserDao;
import com.user.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static UserService userService;
    private static UserDao userDao;
    private static CarDao carDao;
    private static CarBookingService carBookingService;

    public static void main(String[] args) {

        // Test für JavaFaker
        Faker faker = new Faker();
        System.out.println(faker.name().firstName());

        // Booking Implementation
        // CarBookingDao carBookingDao =
        //        new CarBookingFileDataAccessService("bookings.dat");

        CarBookingDao carBookingDao =
                new CarBookingArrayDataAccessService();

        carDao = new CarArrayDataAccessService();
        CarService carService = new CarService(carDao);

        userDao = new UserArrayDataAccessService();
        userService = new UserService(userDao);

        carBookingService = new CarBookingService(
                carService,
                userService,
                carBookingDao
        );

        int userInput;

        while (true) {

            System.out.println("""
                    1 - Book Car
                    2 - Delete Booking
                    3 - View All User Booked Cars
                    4 - View All Bookings
                    5 - View Available Cars
                    6 - View Available Electric Cars
                    7 - View All Users
                    8 - Exit
                    """);

            userInput = scanner.nextInt();

            if (!isValid(userInput)) {
                System.out.println(
                        "Please pick a number between 1 - 8"
                );
            } else {

                if (userInput == 8) {
                    return;
                }

                booking(userInput);
            }
        }
    }

    private static boolean isValid(int userInput) {
        return userInput > 0 && userInput <= 8;
    }

    private static void booking(int userChoice) {

        try {

            switch (userChoice) {

                case 1 -> {

                    LocalDate startDate =
                            readDate("Enter start date");

                    LocalDate endDate =
                            readDate("Enter end date");

                    UUID userId =
                            readUUIDFromUser(
                                    "Put the UUID from User"
                            );

                    String registerNumber =
                            readString(
                                    "Enter Car register Number"
                            );

                    carBookingService.bookCar(
                            userId,
                            registerNumber,
                            startDate,
                            endDate
                    );
                }

                case 2 -> {

                    UUID bookingId =
                            readUUIDFromUser(
                                    "Put the UUID from the Booking"
                            );

                    carBookingService.deleteBooking(bookingId);

                    System.out.println(
                            "Booking deleted with id: "
                                    + bookingId
                    );
                }

                case 3 -> {

                    var users =
                            carBookingService
                                    .viewAllUsersWithBookings();

                    for (User user : users) {
                        System.out.println(user);
                    }
                }

                case 4 -> {

                    var carBookings =
                            carBookingService
                                    .viewAllBookings();

                    for (CarBooking booking : carBookings) {
                        System.out.println(booking);
                    }
                }

                case 5 -> {

                    var cars =
                            carBookingService
                                    .viewAllAvailableCars();

                    for (Car car : cars) {
                        System.out.println(car);
                    }
                }

                case 6 -> {

                    var cars =
                            carBookingService
                                    .viewAllAvailableElectricCars();

                    for (Car car : cars) {
                        System.out.println(car);
                    }
                }

                case 7 -> {

                    var users =
                            carBookingService
                                    .viewAllUsers();

                    for (User user : users) {
                        System.out.println(user);
                    }
                }

                default ->
                        throw new IllegalStateException(
                                "Unexpected value: "
                                        + userChoice
                        );
            }

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static UUID readUUIDFromUser(String text) {

        while (true) {

            try {

                System.out.println(text);

                return UUID.fromString(
                        scanner.next()
                );

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Enter a valid UUID"
                );
            }
        }
    }

    private static String readString(String text) {

        while (true) {

            System.out.println(text);

            String input =
                    scanner.next().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println(
                    "Enter a valid String"
            );
        }
    }

    private static LocalDate readDate(String text) {

        while (true) {

            try {

                System.out.println(text);

                return LocalDate.parse(
                        scanner.next()
                );

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid format for LocalDate"
                );
            }
        }
    }
}