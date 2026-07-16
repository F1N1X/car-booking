package car;

import java.math.BigDecimal;
import java.util.Optional;

public class StaticCarDAO implements CarDAO{
    private static final Car[] cars;

    static {
        cars = new Car[]
                {
                        new Car("B-AB-1001", new BigDecimal("49.99"), Brand.AUDI, false),
                        new Car("M-CD-2002", new BigDecimal("59.99"), Brand.BMW, false),
                        new Car("F-EF-3003", new BigDecimal("89.99"), Brand.MERCEDES, false),
                        new Car("HH-GH-4004", new BigDecimal("79.99"), Brand.TESLA, true),
                        new Car("S-IJ-5005", new BigDecimal("69.99"), Brand.VW, false),
                        new Car("K-KL-6006", new BigDecimal("39.99"), Brand.FIAT, false),
                        new Car("D-MN-7007", new BigDecimal("44.99"), Brand.OPEL, false),
                        new Car("L-OP-8008", new BigDecimal("54.99"), Brand.FORD, false),
                        new Car("AC-QR-9009", new BigDecimal("74.99"), Brand.HYUNDAI, true),
                        new Car("BN-ST-1010", new BigDecimal("64.99"), Brand.KIA, true),
                };
    }

    @Override
    public Optional<Car> getCarByRegistrationNumber(String number) {
        for (Car car : cars)
            if (car.getRegNumber().equals(number))
                return Optional.of(car);

        return Optional.empty();
    }

    @Override
    public Car[] getAllCars() {
        return cars;
    }
}


