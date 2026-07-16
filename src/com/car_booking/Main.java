
// TODO 1. create a new branch called initial-implementation
// TODO 2. create a package with your name. i.e com.franco and move this file inside the new package
// TODO 3. implement https://amigoscode.com/learn/java-cli-build/lectures/3a83ecf3-e837-4ae5-85a8-f8ae3f60f7f5

import booking.CarBooking;
import booking.CarBookingService;
import car.Car;
import user.User;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    private static final CarBookingService carBookingService = new CarBookingService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
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
            if (!isValid(userInput))
                System.out.println("Please pick a number between 1 - 8");
            else {
                if (userInput == 8) return;
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
              case 1 ->  {
                  LocalDate startDate = readDate("Enter start date");
                  LocalDate endDate = readDate("Enter endDate");
                  UUID userId = readUUIDFromUser("Put the UUID from User");
                  String registerNumber = readString("Enter Car register Number");
                  carBookingService.bookCar(userId, registerNumber, startDate, endDate);
              }
              case 2 -> {
                  UUID bookingId = readUUIDFromUser("Put the UUID from the Booking");
                  carBookingService.deleteBooking(bookingId);
                  System.out.println("Booking deleted with id: "+bookingId);
              }
              case 3 -> {
                  User[] users = carBookingService.viewAllUsersWithBookings();
                  for (User user : users)
                      System.out.println(user);
              }
              case 4 -> {
                  CarBooking[] carBookings = carBookingService.viewAllBookings();
                  for (CarBooking booking : carBookings)
                      System.out.println(booking);
              }
              case 5 -> {
                  Car[] cars = carBookingService.viewAllAvailableCars();
                  for (Car car : cars)
                      System.out.println(car);
              }
              case 6 -> {
                  Car[] cars = carBookingService.viewAllAvailableElectricCars();
                  for (Car car : cars)
                          System.out.println(car);
              }
              case 7 -> {
                  User[] users = carBookingService.viewAllUsers();
                  for (User user : users)
                      System.out.println(user);
              }
              default -> throw new IllegalStateException("Unexpected value: " + userChoice);
          }
      } catch (RuntimeException e) {
          System.out.println(e.getMessage());
      }}

    private static UUID readUUIDFromUser(String text) {
        while (true) {
            try {
                System.out.println(text);
                return UUID.fromString(scanner.next());
            } catch (IllegalArgumentException e) {
                System.out.println("Enter a valid UUID");
            }
        }
    }

    private static String readString(String text) {
        while (true) {
            System.out.println(text);

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Enter a valid String");
        }
    }

    private static LocalDate readDate(String text) {
        while (true) {
            try {
                System.out.println(text);
                return LocalDate.parse(scanner.next());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format for LocalDate");
            }
        }
    }
}




