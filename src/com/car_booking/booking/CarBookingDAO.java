package booking;

import car.Car;
import user.User;

import java.util.UUID;

public interface CarBookingDAO {
    void addBooking(CarBooking carBooking);

    boolean isCarBooked(Car car);

    CarBooking[] getAllBookings();

    User[] getAllUserBookedCars();

    boolean deleteBooking(UUID bookingId);
}
