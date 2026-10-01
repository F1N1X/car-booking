package com.booking;

import com.car.Brand;
import com.car.Car;
import com.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CarBookingArrayDataAccessServiceTest {

    private CarBookingArrayDataAccessService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CarBookingArrayDataAccessService();
    }

    @Test
    void isCarBooked() {
        // given
        CarBooking booking = createBooking();
        Car car = booking.getCar();

        underTest.saveBooking(booking);

        // when
        boolean actual = underTest.isCarBooked(car);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void getBookings() {
        // given
        CarBooking booking1 = createBooking();
        CarBooking booking2 = createSecondBooking();

        underTest.saveBooking(booking1);
        underTest.saveBooking(booking2);

        // when
        List<CarBooking> actual = underTest.getBookings();

        // then
        assertThat(actual).containsExactly(
                booking1,
                booking2
        );
    }

    @Test
    void findBookingById() {
        // given
        CarBooking expected = createBooking();

        underTest.saveBooking(expected);

        // when
        CarBooking actual =
                underTest.findBookingById(expected.getId());

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void saveBooking() {
        // given
        CarBooking booking = createBooking();

        // when
        underTest.saveBooking(booking);

        // then
        assertThat(underTest.getBookings())
                .containsExactly(booking);
    }

    @Test
    void getAllUserBookedCars() {
        // given
        CarBooking booking1 = createBooking();
        CarBooking booking2 = createSecondBooking();

        underTest.saveBooking(booking1);
        underTest.saveBooking(booking2);

        // when
        List<User> actual =
                underTest.getAllUserBookedCars();

        // then
        assertThat(actual).containsExactly(
                booking1.getUser(),
                booking2.getUser()
        );
    }

    @Test
    void deleteBooking() {
        // given
        CarBooking booking = createBooking();

        underTest.saveBooking(booking);

        // when
        underTest.deleteBooking(booking.getId());

        // then
        assertThat(
                underTest.findBookingById(booking.getId())
        ).isNull();
    }

    private User createUser() {
        return new User(
                UUID.fromString(
                        "123e4567-e89b-12d3-a456-426614174001"
                ),
                "Test User 1"
        );
    }

    private User createSecondUser() {
        return new User(
                UUID.fromString(
                        "123e4567-e89b-12d3-a456-426614174002"
                ),
                "Test User 2"
        );
    }

    private Car createCar() {
        return new Car(
                UUID.fromString(
                        "123e4567-e89b-12d3-a456-426614174011"
                ),
                "AB-123",
                BigDecimal.valueOf(50),
                Brand.TESLA,
                true
        );
    }

    private Car createSecondCar() {
        return new Car(
                UUID.fromString(
                        "123e4567-e89b-12d3-a456-426614174012"
                ),
                "CD-456",
                BigDecimal.valueOf(40),
                Brand.KIA,
                false
        );
    }

    private CarBooking createBooking() {
        return new CarBooking(
                BigDecimal.valueOf(250),
                LocalDate.of(2026, 10, 10),
                LocalDate.of(2026, 10, 15),
                createCar(),
                createUser(),
                BookingStatus.ACTIVE
        );
    }

    private CarBooking createSecondBooking() {
        return new CarBooking(
                BigDecimal.valueOf(120),
                LocalDate.of(2026, 11, 1),
                LocalDate.of(2026, 11, 4),
                createSecondCar(),
                createSecondUser(),
                BookingStatus.ACTIVE
        );
    }
}