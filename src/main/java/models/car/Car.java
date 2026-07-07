package models.car;

import java.math.BigDecimal;
import java.util.UUID;

public class Car {
    private final UUID id;
    private final String regNumber;
    private final BigDecimal rentalPricePerDay;
    private final Brand brand;
    private final boolean isElectric;

    public Car(UUID id,
               String regNumber,
               BigDecimal rentalPricePerDay,
               Brand brand, boolean isElectric) {
        this.id = id;
        this.regNumber = regNumber;
        this.rentalPricePerDay = rentalPricePerDay;
        this.brand = brand;
        this.isElectric = isElectric;
    }
}
