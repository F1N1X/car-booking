package booking;

import car.Car;
import exceptions.*;
import user.User;

import java.util.*;


public class CarBookingDao {

    private static CarBooking[] carBookings;

    static {
        carBookings = new CarBooking[3];
    }

    public void addBooking(CarBooking carBooking) {

        if (isCarBooked(carBooking.getCar())) throw new CarAlreadyBookedException("car is not available");

        int index = findInsertPoint();

        if (index == -1) {
            index = carBookings.length;
            resize();
        }
        carBookings[index] = carBooking;
    }

    private boolean isCarBooked(Car car) {
        for (CarBooking booking : carBookings) {
            if (booking == null) continue;
            if (car.equals(booking.getCar()))
                return true;
        }
        return false;
    }

    private void resize() {
        carBookings = Arrays.copyOf(carBookings, carBookings.length * 2);
    }

    private int findInsertPoint() {
        int index = -1;
        for (int i = 0; i < carBookings.length; i++) {
            if (carBookings[i] == null)
                return i;
        }
        return index;
    }

    private int countBooking() {
        int count = 0;

        for (CarBooking carBooking : carBookings) {
            if (carBooking != null)
                count++;
        }
        return count;
    }


    public CarBooking[] getAllBookings() {
        if (countBooking() == 0) throw new EmptyBookingException("No booking available");
        return filterCarBookings();
    }

    public User[] getAllUserBookedCars() {

        int findBookings = countBooking();
        if (findBookings == 0) throw new EmptyBookingException("no bookings available");

        User[] userBookedCars = new User[findBookings];
        for (int i = 0; i < carBookings.length; i++) {
            if (carBookings[i] == null) continue;
            userBookedCars[i] = carBookings[i].getUser();
        }
        return userBookedCars;
    }

    public void deleteBooking(UUID bookingId) {
        int indexForDelete = findBookingIndex(bookingId).
                orElseThrow(() -> new NoBookingFoundException("no booking found with booking id"));
        carBookings[indexForDelete] = null;
    }

    private OptionalInt findBookingIndex(UUID bookingId) {
        for (int i = 0; i < carBookings.length; i++) {
            CarBooking booking = carBookings[i];
            if (booking == null)
                continue;
            if (booking.getId().compareTo(bookingId) == 0)
                return OptionalInt.of(i);
        }
        return OptionalInt.empty();
    }

    private CarBooking[] filterCarBookings() {
        CarBooking[] filteredBookings = new CarBooking[countBooking()];
        int index = 0;

        for (CarBooking carBooking : carBookings) {
            if (carBooking != null) {
                filteredBookings[index] = carBooking;
                index++;
            }
        }
        return filteredBookings;
    }
}
