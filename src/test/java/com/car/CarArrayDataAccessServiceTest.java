package com.car;

import com.schwarzwaelder.booking.car.Car;
import com.schwarzwaelder.booking.car.CarArrayDataAccessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class CarArrayDataAccessServiceTest {


    private CarArrayDataAccessService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CarArrayDataAccessService();
    }

    @Test
    void getCarByRegistrationNumber() {
        // when
        Optional<Car> actual = underTest.getCarByRegistrationNumber("B-AB-1001");
        // then
        assertThat(actual).isPresent();
        assertThat(actual.get().getRegNumber())
                .isEqualTo("B-AB-1001");
    }

    @Test
    void getCars() {
        // when
        List<Car> actual = underTest.getCars();

        // then
        assertThat(actual)
                .isNotNull()
                .hasSize(10);

        assertThat(actual)
                .extracting(Car::getRegNumber)
                .contains(
                        "B-AB-1001",
                        "M-CD-2002",
                        "HH-GH-4004"
                );
    }

    @Test
    void findCarById() {
        // given
        Car expected = underTest.getCars().get(0);
        // when
        Car actual = underTest.findCarById(expected.getId());
        // then
        assertThat(actual).isEqualTo(expected);
    }
}