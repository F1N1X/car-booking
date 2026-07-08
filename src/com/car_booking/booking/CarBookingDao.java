package booking;

import car.Car;
import exceptions.CarAllreadyBookedExecption;
import exceptions.NoCapacityException;

public class CarBookingDao {

    private static CarBooking[] carBookings;
    private static int capacity;

    static {
        carBookings = new CarBooking[10];
        capacity = carBookings.length;
    }

    //TODO: length for calculate right place to insert

    public void addBooking(CarBooking carBooking) {

        if (isCarBooked(carBooking.getCar())) throw new CarAllreadyBookedExecption("car is not avaible");
        if (capacity == 0) throw new NoCapacityException("no capacity to store");

        carBookings[carBookings.length % capacity] = carBooking;
    }

    private boolean isCarBooked(Car car) {
        for (CarBooking booking : carBookings)
            if (booking.getCar().getId()
                    .compareTo(car.getId()) == 0)
                return true;
        return false;
    }
}
