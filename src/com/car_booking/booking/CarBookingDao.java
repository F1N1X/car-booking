package booking;

import car.Car;
import exceptions.*;
import user.User;

import java.util.Objects;
import java.util.UUID;


public class CarBookingDao {

    private static CarBooking[] carBookings;
    private static int capacity;

    static {
        carBookings = new CarBooking[3];
        capacity = carBookings.length;
    }

    public void addBooking(CarBooking carBooking) {
        if (capacity == 0) throw new NoCapacityException("no capacity to store");
        if (isCarBooked(carBooking.getCar())) throw new CarAlreadyBookedException("car is not available");

        carBookings[carBookings.length - capacity] = carBooking;
        capacity--;
    }

    private boolean isCarBooked(Car car) {
        if (Objects.isNull(carBookings[0])) return false;
        for (CarBooking booking : carBookings)
            if (booking.getCar().getId()
                    .compareTo(car.getId()) == 0)
                return true;
        return false;
    }

    public CarBooking[] getAllBookings() {
        if (capacity == 10) throw new EmptyBookingException("No booking available");
        return carBookings;
    }

    public Car[] getAllAvailableCars(Car[] allCars) {

        Car[] avaibleCar = new Car[allCars.length];

        if (capacity == carBookings.length) return allCars;

        int insertPoint = 0;
        int foundCars = 0;
        for (Car car : allCars) {
            if (!containsCar(car)) {
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
    
    private boolean containsCar(Car searchCar) {
        for (CarBooking booking : carBookings) {
            if (Objects.isNull(booking)) continue;
            if (booking.getCar().equals(searchCar))
                return true;
        }
     return false;
    }

    public User[] getAllUserBookedCars() {

        if (capacity == carBookings.length) throw new EmptyBookingException("no bookings available");

        User[] userBookedCars = new User[carBookings.length];
        for (int i = 0; i < carBookings.length; i++) {
            if (Objects.isNull(carBookings[i])) continue;
            userBookedCars[i] = carBookings[i].getUser();
        }
        return userBookedCars;
    }

    public void deleteBooking(UUID bookingId) {

        if (!containsId(bookingId)) throw new NoBookingFoundException("no booking found with id");

        CarBooking[] clearedArray = new CarBooking[carBookings.length];
        int insertPoint = 0;
        for (int i = 0; i < carBookings.length; i++) {
            if (Objects.isNull(carBookings[i])) continue;
            if (!(carBookings[i].getId().compareTo(bookingId) == 0)) {
                clearedArray[insertPoint] = carBookings[i];
                insertPoint++;
            }
        }
        carBookings = clearedArray;
        capacity++;
    }

    private boolean containsId(UUID bookingId) {
        for (CarBooking booking : carBookings) {
            if (Objects.isNull(booking)) continue;
            if (booking.getId().compareTo(bookingId) == 0)
                return true;
        }
        return false;
    }
}
