package booking;

import car.Car;
import user.User;

import java.util.*;

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
        for (var carBooking : carBookings)
            if (carBooking.getId().equals(bookingId))
                return carBooking;
        return null;
    }

    @Override
    public void saveBooking(CarBooking booking) {
        carBookings.add(booking);
    }

    public List<User> getAllUserBookedCars() {
        int findBookings = carBookings.size();
        if (findBookings == 0) return new ArrayList<>();

        List<User> userBookedCars = new ArrayList<User>();
        for (int i = 0; i < carBookings.size(); i++)
            userBookedCars.add(carBookings.get(i).getUser());

        return userBookedCars;
    }

    public void deleteBooking(UUID bookingId) {
           carBookings.remove(findBookingById(bookingId));
    }
}
