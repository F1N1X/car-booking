package booking;

import car.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarBookingFileDataAccessService implements CarBookingDao {

    private final String filePath;

    public CarBookingFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<CarBooking> getBookings() {

        File file = new File(filePath);

        if (fileNotExistOrEmpty(file)) {
            return new ArrayList<>();
        }

        List<CarBooking> carBookings = new ArrayList<>();

        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(new FileInputStream(file))) {

            while (true) {
                CarBooking carBooking =
                        (CarBooking) objectInputStream.readObject();

                carBookings.add(carBooking);
            }

        } catch (EOFException e) {
            return carBookings;

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean fileNotExistOrEmpty(File file) {
        return !file.exists() || file.length() == 0;
    }

    @Override
    public CarBooking findBookingById(UUID bookingId) {

        File file = new File(filePath);

        if (fileNotExistOrEmpty(file)) {
            return null;
        }

        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(new FileInputStream(file))) {

            while (true) {

                CarBooking booking =
                        (CarBooking) objectInputStream.readObject();

                if (booking.getId().equals(bookingId)) {
                    return booking;
                }
            }

        } catch (EOFException e) {
            return null;

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveBooking(CarBooking booking) {

        File file = new File(filePath);

        boolean append = !fileNotExistOrEmpty(file);

        try (
                FileOutputStream fileOutputStream =
                        new FileOutputStream(file, append);

                ObjectOutputStream objectOutputStream =
                        append
                                ? new AppendObjectOutputStream(fileOutputStream)
                                : new ObjectOutputStream(fileOutputStream)
        ) {

            objectOutputStream.writeObject(booking);

        } catch (IOException e) {
            throw new RuntimeException(
                    "booking can't be saved",
                    e
            );
        }
    }

    @Override
    public void deleteBooking(UUID bookingId) {

        List<CarBooking> carBookings =
                readBookingsExcludingId(bookingId);

        if (carBookings == null) {
            return;
        }

        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(
                             new FileOutputStream(filePath)
                     )) {

            for (CarBooking booking : carBookings) {
                outputStream.writeObject(booking);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<CarBooking> readBookingsExcludingId(UUID bookingId) {

        File file = new File(filePath);

        if (fileNotExistOrEmpty(file)) {
            return null;
        }

        List<CarBooking> carBookings = new ArrayList<>();

        boolean bookingFound = false;

        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(
                             new FileInputStream(filePath)
                     )) {

            while (true) {

                CarBooking booking =
                        (CarBooking) objectInputStream.readObject();

                if (booking.getId().equals(bookingId)) {
                    bookingFound = true;
                } else {
                    carBookings.add(booking);
                }
            }

        } catch (EOFException e) {

            if (!bookingFound) {
                return null;
            }

            return carBookings;

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isCarBooked(Car car) {

        if (car == null) {
            throw new IllegalArgumentException("car can't be null");
        }

        File file = new File(filePath);

        if (fileNotExistOrEmpty(file)) {
            return false;
        }

        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(new FileInputStream(file))) {

            while (true) {

                CarBooking booking =
                        (CarBooking) objectInputStream.readObject();

                if (booking.getCar().getId().equals(car.getId())) {
                    return true;
                }
            }

        } catch (EOFException e) {
            return false;

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private class AppendObjectOutputStream extends ObjectOutputStream {

        public AppendObjectOutputStream(OutputStream outputStream)
                throws IOException {
            super(outputStream);
        }

        @Override
        protected void writeStreamHeader() {
        }
    }
}