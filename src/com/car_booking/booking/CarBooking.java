package booking;

import car.Car;
import user.User;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CarBooking implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    private UUID id;
    private User user;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private BookingStatus status;
    private LocalDateTime bookedAt;

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



    @Override
    public String toString() {
        return  "id=" + id +
                "\nuser=" + user +
                "\ncar=" + car +
                "\nstartDate=" + startDate +
                "\tendDate=" + endDate +
                "\nprice=" + price +
                "\nbookedAt=" + bookedAt;
    }
}
