package com.schwarzwaelder.booking;

import com.schwarzwaelder.booking.car.Brand;
import com.schwarzwaelder.booking.car.Car;
import com.schwarzwaelder.booking.car.CarService;
import com.schwarzwaelder.booking.exceptions.CarAlreadyBookedException;
import com.schwarzwaelder.booking.exceptions.InvalidBookingPeriodException;
import com.schwarzwaelder.booking.exceptions.NoCarFoundException;
import com.schwarzwaelder.booking.exceptions.NoUserFoundException;
import com.schwarzwaelder.booking.user.UserService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.schwarzwaelder.booking.user.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarBookingServiceTest {

    @Mock
    CarService carService;

    @Mock
    UserService userService;

    @Mock
    private CarBookingDao carBookingDao;

    @InjectMocks
    CarBookingService underTest;



    @Test
    void bookCar() {
        // given
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

        // when
        when(userService.getUserByID(user.getId())).thenReturn(Optional.of(user));
        when(carService.getCarByRegistrationNumber(car.getRegNumber())).thenReturn(Optional.of(car));
        when(carBookingDao.isCarBooked(any(Car.class))).thenReturn(false);
        underTest.bookCar(user.getId()
                ,car.getRegNumber(),
                LocalDate.of(2026, 10, 10),
                LocalDate.of(2026, 10, 15));
        // then
        verify(userService).getUserByID(user.getId());
        verify(carService).getCarByRegistrationNumber(car.getRegNumber());
        verify(carBookingDao).isCarBooked(car);
        verify(carBookingDao).saveBooking(any(CarBooking.class));
    }

    @Test
    void deleteBooking() {
        // given
        UUID bookingId = UUID.fromString("123e4567-e89b-12d3-a456-426614174003");
        // when
        underTest.deleteBooking(bookingId);
        // then
        verify(carBookingDao).deleteBooking(bookingId);
    }

    @Test
    void viewAllUsersWithBookings() {
        // given
        User user1 = new User(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174001"),
                "Test User 1"
        );

        User user2 = new User(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174002"),
                "Test User 2"
        );

        Car car1 = new Car(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174011"),
                "AB-123",
                BigDecimal.valueOf(50),
                Brand.TESLA,
                true
        );

        Car car2 = new Car(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174012"),
                "CD-456",
                BigDecimal.valueOf(40),
                Brand.KIA,
                false
        );

        CarBooking booking1 = new CarBooking(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174021"),
                user1,
                car1,
                LocalDate.of(2026, 10, 10),
                LocalDate.of(2026, 10, 15),
                BigDecimal.valueOf(250),
                BookingStatus.ACTIVE,
                LocalDateTime.of(2026, 10, 1, 12, 0)
        );

        CarBooking booking2 = new CarBooking(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174022"),
                user2,
                car2,
                LocalDate.of(2026, 11, 1),
                LocalDate.of(2026, 11, 4),
                BigDecimal.valueOf(120),
                BookingStatus.ACTIVE,
                LocalDateTime.of(2026, 10, 2, 14, 30)
        );

        List<CarBooking> bookings = List.of(
                booking1,
                booking2
        );
        // when
        when(carBookingDao.getBookings()).thenReturn(bookings);
        List<User> actual = underTest.viewAllUsersWithBookings();
        // then
        assertThat(actual).containsExactly(user1, user2);
        verify(carBookingDao).getBookings();
    }

    @Test
    void viewAllBookings() {
        // given
        User user1 = new User(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174001"),
                "Test User 1"
        );

        User user2 = new User(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174002"),
                "Test User 2"
        );

        Car car1 = new Car(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174011"),
                "AB-123",
                BigDecimal.valueOf(50),
                Brand.TESLA,
                true
        );

        Car car2 = new Car(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174012"),
                "CD-456",
                BigDecimal.valueOf(40),
                Brand.KIA,
                false
        );

        CarBooking booking1 = new CarBooking(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174021"),
                user1,
                car1,
                LocalDate.of(2026, 10, 10),
                LocalDate.of(2026, 10, 15),
                BigDecimal.valueOf(250),
                BookingStatus.ACTIVE,
                LocalDateTime.of(2026, 10, 1, 12, 0)
        );

        CarBooking booking2 = new CarBooking(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174022"),
                user2,
                car2,
                LocalDate.of(2026, 11, 1),
                LocalDate.of(2026, 11, 4),
                BigDecimal.valueOf(120),
                BookingStatus.ACTIVE,
                LocalDateTime.of(2026, 10, 2, 14, 30)
        );

        List<CarBooking> expected = List.of(
                booking1,
                booking2
        );
        // when
        when(carBookingDao.getBookings()).thenReturn(expected);
        List<CarBooking> actual = underTest.viewAllBookings();
        // then
        assertThat(actual).isEqualTo(expected);
        verify(carBookingDao).getBookings();
    }

    @Test
    void viewAllAvailableCars() {
            // given
            List<Car> expected = List.of(
                    new Car(
                            UUID.fromString("123e4567-e89b-12d3-a456-426614174101"),
                            "AB-123",
                            BigDecimal.valueOf(50),
                            Brand.TESLA,
                            true
                    ),
                    new Car(
                            UUID.fromString("123e4567-e89b-12d3-a456-426614174102"),
                            "CD-456",
                            BigDecimal.valueOf(40),
                            Brand.KIA,
                            false
                    ),
                    new Car(
                            UUID.fromString("123e4567-e89b-12d3-a456-426614174103"),
                            "EF-789",
                            BigDecimal.valueOf(65),
                            Brand.BMW,
                            false
                    )
            );
            when(carService.getAllCars()).thenReturn(expected);
            expected.forEach(car ->
                    when(carBookingDao.isCarBooked(car)).thenReturn(false)
            );
            // when
            List<Car> actual = underTest.viewAllAvailableCars();
            // then
            assertThat(actual).isEqualTo(expected);
            verify(carService).getAllCars();
            verify(carBookingDao, times(3)).isCarBooked(any(Car.class));
    }

    @Test
    void viewAllAvailableElectricCars() {
        // given
        Car car1 = new Car(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174101"),
                "AB-123",
                BigDecimal.valueOf(50),
                Brand.TESLA,
                true
        );

        Car car2 = new Car(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174102"),
                "CD-456",
                BigDecimal.valueOf(40),
                Brand.KIA,
                false
        );

        Car car3 = new Car(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174103"),
                "EF-789",
                BigDecimal.valueOf(65),
                Brand.BMW,
                true
        );

        List<Car> given = List.of(
                car1,
                car2,
                car3
        );

        when(carService.getAllCars()).thenReturn(given);
        when(carBookingDao.isCarBooked(car1)).thenReturn(false);
        when(carBookingDao.isCarBooked(car2)).thenReturn(false);
        when(carBookingDao.isCarBooked(car3)).thenReturn(false);
        // when
        List<Car> actual = underTest.viewAllAvailableElectricCars();
        // then
        assertThat(actual).containsExactly(car1, car3);
        verify(carService).getAllCars();
        verify(carBookingDao, times(3)).isCarBooked(any(Car.class));
    }

    @Test
    void viewAllUsers() {
        // given
        List<User> expected = List.of(
                new User(
                        UUID.fromString("123e4567-e89b-12d3-a456-426614174001"),
                        "Max Mustermann"
                ),
                new User(
                        UUID.fromString("123e4567-e89b-12d3-a456-426614174002"),
                        "Anna Schmidt"
                ),
                new User(
                        UUID.fromString("123e4567-e89b-12d3-a456-426614174003"),
                        "John Doe"
                )
        );
        // when
        when(userService.getAllUsers()).thenReturn(expected);
        List<User> actual = underTest.viewAllUsers();
        // then
        verify(userService).getAllUsers();
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    void shouldThrowWhenBookingPeriodIsInvalid() {
        // given
        UUID userId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");

        // when
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(3);

        //then
        assertThatThrownBy(() ->
                underTest.bookCar(
                        userId,
                        "AB-123",
                        startDate,
                        endDate
                )
        ).isInstanceOf(InvalidBookingPeriodException.class);
        verifyNoInteractions(userService);
        verifyNoInteractions(carService);
        verify(carBookingDao, never()).saveBooking(any());
    }
    @Test
    void shouldThrowWhenUserDoesNotExist() {
        // given
        UUID userId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(5);
        // when
        when(userService.getUserByID(userId)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() ->
                underTest.bookCar(
                        userId,
                        "AB-123",
                        startDate,
                        endDate
                )
        ).isInstanceOf(NoUserFoundException.class);
        verify(userService).getUserByID(userId);
        verifyNoInteractions(carService);
        verify(carBookingDao, never()).saveBooking(any());
    }
    @Test
    void shouldThrowWhenCarDoesNotExist() {
        // given
        User user = new User(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174001"),
                "Test User"
        );
        String registerNumber = "AB-123";
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(5);
        // when
        when(userService.getUserByID(user.getId()))
                .thenReturn(Optional.of(user));

        when(carService.getCarByRegistrationNumber(registerNumber))
                .thenReturn(Optional.empty());
         //then
        assertThatThrownBy(() ->
                underTest.bookCar(
                        user.getId(),
                        registerNumber,
                        startDate,
                        endDate
                )
        ).isInstanceOf(NoCarFoundException.class);
        verify(userService).getUserByID(user.getId());
        verify(carService).getCarByRegistrationNumber(registerNumber);
        verify(carBookingDao, never()).saveBooking(any());
    }
    @Test
    void shouldThrowWhenCarIsAlreadyBooked() {
        // given
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
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(5);
        // when
        when(userService.getUserByID(user.getId()))
                .thenReturn(Optional.of(user));
        when(carService.getCarByRegistrationNumber(car.getRegNumber()))
                .thenReturn(Optional.of(car));
        when(carBookingDao.isCarBooked(car))
                .thenReturn(true);
        // then
        assertThatThrownBy(() ->
                underTest.bookCar(
                        user.getId(),
                        car.getRegNumber(),
                        startDate,
                        endDate
                )
        ).isInstanceOf(CarAlreadyBookedException.class);
        verify(userService).getUserByID(user.getId());
        verify(carService).getCarByRegistrationNumber(car.getRegNumber());
        verify(carBookingDao).isCarBooked(car);
        verify(carBookingDao, never()).saveBooking(any());
    }
}