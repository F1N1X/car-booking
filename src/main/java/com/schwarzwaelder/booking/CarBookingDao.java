package com.schwarzwaelder.booking;

import com.schwarzwaelder.booking.car.Car;

import java.util.List;
import java.util.UUID;

public interface CarBookingDao {
    List<CarBooking> getBookings();
    CarBooking findBookingById(UUID bookingId);
    void saveBooking(CarBooking booking);
    void deleteBooking(UUID bookingId);

    boolean isCarBooked(Car car);
}
