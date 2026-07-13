
// TODO 1. create a new branch called initial-implementation
// TODO 2. create a package with your name. i.e com.franco and move this file inside the new package
// TODO 3. implement https://amigoscode.com/learn/java-cli-build/lectures/3a83ecf3-e837-4ae5-85a8-f8ae3f60f7f5

import booking.CarBookingService;

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
                  LocalDate startDate, endDate;
                  do {
                      startDate = readDate("Enter start date");
                      endDate = readDate("Enter endDate");
                  } while (!isValidBookingPeriod(startDate, endDate) && invalidStartAndEnd(startDate, endDate));

                  UUID userId = readUUIDFromUser("Put the UUID from User");
                  String registerNumber = readString("Enter Car register Number");

                  carBookingService.bookCar(userId, registerNumber, startDate, endDate);
              }
              case 2 -> {
                  UUID bookingId = readUUIDFromUser("Put the UUID from the Booking");
                  carBookingService.deleteBooking(bookingId);
              }
              case 3 -> carBookingService.viewAllUserBookingCars();
              case 4 -> carBookingService.viewAllBookings();
              case 5 -> carBookingService.viewAllAllAvailableCars();
              case 6 -> carBookingService.viewAllAvailableElectricCars();
              case 7 -> carBookingService.viewAllUsers();
              default -> throw new IllegalStateException("Unexpected value: " + userChoice);
          }
      } catch (RuntimeException e) {
          System.out.println(e.getMessage());
      }}

    private static boolean invalidStartAndEnd(LocalDate start, LocalDate end) {
        boolean isInPast = start.isBefore(LocalDate.now()) || end.isBefore(LocalDate.now());
        if (isInPast)
            System.out.println("input is in the past");
        return isInPast;
    }

    private static boolean isValidBookingPeriod(LocalDate start, LocalDate end) {
        if (!start.isBefore(end) && !invalidStartAndEnd(start, end)) {
            System.out.println("The start date cannot be in the past or after the end date.");
            return false;
        }
        return true;
    }
    private static UUID readUUIDFromUser(String text) {
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

    private static String readString(String text) {
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

    private static LocalDate readDate(String text) {
        LocalDate inputDate;
        try {
            System.out.println(text);
            inputDate = LocalDate.parse(scanner.next());

        } catch (DateTimeParseException | IllegalArgumentException e) {
            return readDate(text);
        }
        return inputDate;
    }
}




