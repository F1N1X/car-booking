package booking;

import car.Car;
import user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CarBooking {
    private final UUID id;
    private final User user;
    private final Car car;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final BigDecimal price;
    private BookingStatus status;
    private final LocalDateTime bookedAt;

    public CarBooking(BigDecimal price,
                      LocalDate startDate,
                      LocalDate endDate,
                      Car car,
                      User user,
                      BookingStatus status) {
        this.price = price;
        this.bookedAt = LocalDateTime.now();
        this.endDate = endDate;
        this.startDate = startDate;
        this.car = car;
        this.user = user;
        this.id = UUID.randomUUID();
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Car getCar() {
        return car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    @Override
    public String toString() {
        return "CarBooking created \n" +
                "\nuser=" + user +
                "\ncar=" + car +
                "\nstartDate=" + startDate +
                "\tendDate=" + endDate +
                "\nprice=" + price +
                "bookedAt=" + bookedAt;
    }
}
