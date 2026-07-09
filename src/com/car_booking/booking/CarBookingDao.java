package booking;

import car.Car;
import exceptions.*;
import user.User;

import java.util.Arrays;
import java.util.UUID;


public class CarBookingDao {

    private static CarBooking[] carBookings;
    private static int capacity;

    static {
        carBookings = new CarBooking[10];
        capacity = carBookings.length;
    }

    public void addBooking(CarBooking carBooking) {

        if (isCarBooked(carBooking.getCar())) throw new CarAllreadyBookedExecption("car is not available");
        if (capacity == 0) throw new NoCapacityException("no capacity to store");

        carBookings[(carBookings.length - 1) % capacity] = carBooking;
    }

    private boolean isCarBooked(Car car) {
        for (CarBooking booking : carBookings)
            if (booking.getCar().getId()
                    .compareTo(car.getId()) == 0)
                return true;
        return false;
    }

    public String getAllBookings() {
        if (capacity == 10) throw new EmptyBookingException("No booking available");
        return Arrays.toString(carBookings);
    }

    public Car[] getAllAvaiableCars(Car[] allCars) {

        Car[] avaibleCar = new Car[allCars.length];

        if (capacity == carBookings.length) return allCars;

        int insertPoint = 0;
        int foundCars = 0;
        for (Car car : allCars) {
            if (!cointainCar(car)) {
                avaibleCar[insertPoint] = car;
                insertPoint++;
                foundCars++;
            }
        }

        if (foundCars == 0) throw new NoAvaibleCarsException("no cars available");

        return removeNullElement(avaibleCar, foundCars);
    }

    private Car[] removeNullElement(Car[] filteredCars, int foundCars) {
        Car[] avaibleCars = new Car[foundCars];
        for (int i = 0; i < foundCars; i++) {
            avaibleCars[i] = filteredCars[i];
        }
        return avaibleCars;
    }
    
    private boolean cointainCar(Car searchCar) {
        for (CarBooking booking : carBookings)
            if (booking.getCar().equals(searchCar))
                return true;
     return false;
    }

    public User[] getAllUserBookedCars() {

        if (capacity == 10) throw new EmptyBookingException("no bookings available");

        User[] userBookedCars = new User[carBookings.length];
        for (int i = 0; i < carBookings.length; i++)
            userBookedCars[i] = carBookings[i].getUser();
        return userBookedCars;
    }

    public void deleteBooking(UUID bookingId) {

        if (!containsId(bookingId)) throw new NoBookingFoundException("no booking found with id");

        CarBooking[] clearedArray = new CarBooking[carBookings.length];
        int insertPoint = 0;
        for (int i = 0; i < carBookings.length; i++) {
            if (!(carBookings[i].getId().compareTo(bookingId) == 0)) {
                clearedArray[insertPoint] = carBookings[i];
                insertPoint++;
            }
        }
        capacity++;
    }

    private boolean containsId(UUID bookingId) {
        for (CarBooking booking : carBookings)
            if (booking.getId().compareTo(bookingId) == 0)
                return true;
        return false;
    }
}
