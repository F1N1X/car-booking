
// TODO 1. create a new branch called initial-implementation
// TODO 2. create a package with your name. i.e com.franco and move this file inside the new package
// TODO 3. implement https://amigoscode.com/learn/java-cli-build/lectures/3a83ecf3-e837-4ae5-85a8-f8ae3f60f7f5

import booking.CarBookingService;

import java.util.Scanner;

public class Main {

    private static final CarBookingService carBookingService = new CarBookingService();

    // TODO: Error Handling Exceptions

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
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

    private static void booking(int userChoice) throws RuntimeException{
      try {
          switch (userChoice) {
              case 1 -> carBookingService.bookCar();
              case 2 -> carBookingService.deleteBooking();
              case 3 -> carBookingService.viewAllUserBookingCars();
              case 4 -> carBookingService.viewAllBookings();
              case 5 -> carBookingService.viewAllAllAvailableCars();
              case 6 -> carBookingService.viewAllAvailableElectroCars();
              case 7 -> carBookingService.viewAllUsers();
              default -> throw new IllegalStateException("Unexpected value: " + userChoice);
          }
      } catch (RuntimeException e) {
          System.out.println(e.getMessage());
      }
    }
}
