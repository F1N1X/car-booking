package booking;

import car.Car;
import user.User;

import java.util.*;
import java.util.stream.Collectors;

public class CarBookingArrayDataAccessService implements CarBookingDao{

    private static List<CarBooking> carBookings;

    static {
        carBookings = new ArrayList<>();
    }

    public boolean isCarBooked(Car car) {
        return carBookings.stream()
                .anyMatch(c -> c.getCar().equals(car));
    }


    public List<CarBooking> getBookings() {
        return carBookings;
    }

    @Override
    public CarBooking findBookingById(UUID bookingId) {
        return carBookings.stream()
                .filter( b -> b.getId().equals(bookingId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void saveBooking(CarBooking booking) {
        carBookings.add(booking);
    }

    public List<User> getAllUserBookedCars() {
        int findBookings = carBookings.size();
        if (findBookings == 0) return new ArrayList<>();

        return carBookings.stream()
                .map(CarBooking::getUser)
                .toList();
    }

    public void deleteBooking(UUID bookingId) {
           carBookings.remove(findBookingById(bookingId));
    }
}
