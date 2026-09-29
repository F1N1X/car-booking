package com.car;

import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class CarFakerDataAccessService implements CarDao{

    private final List<Car> cars;
    private final Faker faker;
    private final Brand[] brands;

    public CarFakerDataAccessService() {

        faker = new Faker();
        cars = new ArrayList<>();

        brands = Brand.values();

        for (int i = 0; i < 20; i++) {
            cars.add(new Car(
                    faker.letterify("??").toUpperCase()
                            + "-"
                            + faker.number().numberBetween(100, 999),

                    BigDecimal.valueOf(
                            faker.number().randomDouble(2, 30, 150)
                    ),

                    brands[
                            faker.number().numberBetween(0, brands.length)
                            ],

                    faker.bool().bool()
            ));
        }


    }

    @Override
    public List<Car> getCars() {
    return cars;
    }

    @Override
    public Car findCarById(UUID id) {
        return cars.stream().filter(
                c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Optional<Car> getCarByRegistrationNumber(String number) {
        return cars.stream().filter(
                c -> c.getRegNumber().equals(number)
        ).findFirst();
    }
}
