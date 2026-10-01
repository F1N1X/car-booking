package com.booking;

import com.car.Brand;
import com.car.Car;
import com.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CarBookingFileDataAccessServiceTest {

    @TempDir
    Path tempDir;

    private CarBookingFileDataAccessService underTest;

    @BeforeEach
    void setUp() {
        Path file = tempDir.resolve("bookings.dat");
        underTest = new CarBookingFileDataAccessService(
                file.toString());
    }

    @Test
    void getBookings() {
        // given
        CarBooking booking1 = createBooking();
        CarBooking booking2 = createBooking();
        underTest.saveBooking(booking1);
        underTest.saveBooking(booking2);
        // when
        List<CarBooking> actual = underTest.getBookings();
        // then
        assertThat(actual)
                .containsExactly(booking1, booking2);
    }

    @Test
    void findBookingById() {
        // given
        CarBooking given = createBooking();
        underTest.saveBooking(given);
        // when
        CarBooking actual = underTest.findBookingById(given.getId());
        // then
        assertThat(actual).isEqualTo(given);
    }

    @Test
    void saveBooking() {
        // given
        CarBooking booking = createBooking();
        // when
        underTest.saveBooking(booking);
        // then
        List<CarBooking> actual = underTest.getBookings();
        assertThat(actual)
                .hasSize(1);
        assertThat(underTest.getBookings())
                .containsExactly(booking);
    }

    @Test
    void deleteBooking() {
        // given
        CarBooking given = createBooking();
        underTest.saveBooking(given);
        // when
        underTest.deleteBooking(given.getId());
        CarBooking actual = underTest.findBookingById(given.getId());
        // then
        assertThat(actual).isNull();
    }

    @Test
    void isCarBooked() {
        // given
        CarBooking booking = createBooking();
        underTest.saveBooking(booking);
        // when
        boolean actual = underTest.isCarBooked(booking.getCar());
        // then
        assertThat(actual).isTrue();
    }
    private CarBooking createBooking() {
        User user = new User(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174001"),
                "Test User"
        );

        Car car = new Car(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174002"),
                "AB-123",
                BigDecimal.valueOf(50),
                Brand.TESLA,
                true
        );

        return new CarBooking(
                BigDecimal.valueOf(250),
                LocalDate.of(2026, 10, 10),
                LocalDate.of(2026, 10, 15),
                car,
                user,
                BookingStatus.ACTIVE
        );
    }
}