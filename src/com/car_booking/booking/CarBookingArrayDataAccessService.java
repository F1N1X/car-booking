package booking;

import car.Car;
import user.User;

import java.util.*;

public class CarBookingArrayDataAccessService implements CarBookingDao{

    private static CarBooking[] carBookings;

    static {
        carBookings = new CarBooking[3];
    }


    public boolean isCarBooked(Car car) {
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


    public CarBooking[] getBookings() {
        if (countBooking() == 0) return new CarBooking[0];
        return filterCarBookings();
    }

    @Override
    public CarBooking findBookingById(UUID bookingId) {
        for (var booking : carBookings)
            if (booking.getId().compareTo(bookingId) == 0)
                return booking;
        return null;
    }

    @Override
    public void saveBooking(CarBooking booking) {
        int index = findInsertPoint();

        if (index == -1) {
            index = carBookings.length;
            resize();
        }
        carBookings[index] = booking;
    }

    public User[] getAllUserBookedCars() {

        int findBookings = countBooking();
        if (findBookings == 0) return new User[0];

        int indexForInsert = 0;

        User[] userBookedCars = new User[findBookings];
        for (int i = 0; i < carBookings.length; i++) {
            if (carBookings[i] == null) continue;
            userBookedCars[indexForInsert++] = carBookings[i].getUser();
        }
        return userBookedCars;
    }

    public void deleteBooking(UUID bookingId) {
            OptionalInt indexForDelete = findBookingIndex(bookingId);
            if (indexForDelete.isPresent())
                carBookings[indexForDelete.getAsInt()] = null;
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
