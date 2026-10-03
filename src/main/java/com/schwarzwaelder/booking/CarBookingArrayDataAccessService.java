package com.schwarzwaelder.booking;

import com.schwarzwaelder.booking.car.Car;
import com.schwarzwaelder.booking.user.User;

import java.util.*;

public class CarBookingArrayDataAccessService implements CarBookingDao{

    private final List<CarBooking> carBookings;

    public CarBookingArrayDataAccessService() {
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
        return carBookings.stream()
                .map(CarBooking::getUser)
                .toList();
    }

    public void deleteBooking(UUID bookingId) {
           carBookings.remove(findBookingById(bookingId));
    }
}


