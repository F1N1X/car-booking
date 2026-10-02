package com.car;

import com.schwarzwaelder.booking.car.Brand;
import com.schwarzwaelder.booking.car.Car;
import com.schwarzwaelder.booking.car.CarDao;
import com.schwarzwaelder.booking.car.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    CarDao carDao;

    @InjectMocks
    CarService underTest;



    @Test
    void getCarByRegistrationNumber() {
        // given
        Car expectedCar = new Car("abc-123", BigDecimal.valueOf(40), Brand.KIA, false);
        when(carDao.getCarByRegistrationNumber("abc-123")).thenReturn(Optional.of(expectedCar));
        // when
        Optional<Car> actual = underTest.getCarByRegistrationNumber(expectedCar.getRegNumber());
        // then
        assertTrue(actual.isPresent());
        assertThat(actual.get()).isEqualTo(expectedCar);
        verify(carDao)
                .getCarByRegistrationNumber(expectedCar.getRegNumber());
    }

    @Test
    void getAllCars() {
        // given
        List<Car> expected = new ArrayList<>(
                List.of(
                        new Car(
                                UUID.fromString("123e4567-e89b-12d3-a456-426614174111"),
                                "AB-123",
                                BigDecimal.valueOf(40),
                                Brand.KIA,
                                false
                        ),
                        new Car(
                                UUID.fromString("123e4567-e89b-12d3-a456-426614174112"),
                                "CD-456",
                                BigDecimal.valueOf(55),
                                Brand.TESLA,
                                true
                        )
                )
        );
        // when
        when(carDao.getCars()).thenReturn(expected);
        List<Car> actual = underTest.getAllCars();
        // then
        verify(carDao).getCars();
        assertThat(actual).isEqualTo(expected);
    }


}