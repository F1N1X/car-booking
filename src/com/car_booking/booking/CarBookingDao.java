package booking;

import car.Car;

import java.util.UUID;

public interface CarBookingDao {
    CarBooking[] getBookings();
    CarBooking findBookingById(UUID bookingId);
    void saveBooking(CarBooking booking);
    void deleteBooking(UUID bookingId);

    boolean isCarBooked(Car car);
}
