package booking;

import car.Car;

import java.io.*;
import java.util.UUID;


public class CarBookingFileDataAccessService implements CarBookingDao{

    private final String filePath;

    public CarBookingFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CarBooking[] getBookings() {

        File file = new File(filePath);

        if (fileNotExistOrEmpty(file))
            return new CarBooking[0];

        CarBooking[] carBookings = new CarBooking[countBookings()];
        int insertPoint = 0;

        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(new FileInputStream(file))) {
            CarBooking carBooking;
            while(insertPoint < carBookings.length) {
                carBooking =(CarBooking) objectInputStream.readObject();
                carBookings[insertPoint++] = carBooking;
            }
        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return carBookings;
    }

    private static boolean fileNotExistOrEmpty(File file) {
        return !file.exists() || file.length() == 0;
    }

    private int countBookings() {
        File file = new File(filePath);
        int foundBookings = 0;

        if (fileNotExistOrEmpty(file))
            return foundBookings;

        try (ObjectInputStream objectInputStream =
                new ObjectInputStream(new FileInputStream(file))) {

            while(true) {
                objectInputStream.readObject();
                foundBookings++;
            }
        }
        catch (EOFException e) {
            return foundBookings;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CarBooking findBookingById(UUID bookingId) {

        File file = new File(filePath);

        if (fileNotExistOrEmpty(file))
            return null;

        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(new FileInputStream(file))) {

            CarBooking booking;

            while (true) {
                booking = (CarBooking)objectInputStream.readObject();
                if (booking.getId().equals(bookingId))
                    return booking;
            }
        }
        catch (EOFException e) {
            return null;
        }
        catch (IOException | ClassNotFoundException e) {
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
                    "booking can't saved",
                    e
            );
        }
    }

    @Override
    public void deleteBooking(UUID bookingId) {

        int bookingCount = countBookings();

        if (bookingCount == 0 || findBookingById(bookingId) == null)
            return;

        CarBooking[] carBookings = new CarBooking[bookingCount-1];

        readBookingExcludingId(bookingId, carBookings);

        try (ObjectOutputStream outputStream =
                new ObjectOutputStream(new FileOutputStream(filePath))) {

            for (CarBooking booking : carBookings)
                outputStream.writeObject(booking);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CarBooking[] readBookingExcludingId(UUID bookingId, CarBooking[] carBookings) {

        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(new FileInputStream(filePath))) {

            CarBooking booking;
            int insertPoint = 0;

            while (true) {
                booking = (CarBooking)objectInputStream.readObject();
                if (!booking.getId().equals(bookingId))
                    carBookings[insertPoint++] = booking;
            }

        } catch (EOFException _) {
            return carBookings;
        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isCarBooked(Car car) {

        File file = new File(filePath);

        if (car == null)
            throw new IllegalArgumentException("car can't null");

        if (fileNotExistOrEmpty(file))
            return false;

        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(new FileInputStream(file))) {

            CarBooking booking;
            while (true) {
                booking = (CarBooking) objectInputStream.readObject();
                System.out.println(
                        "Car serialisierbar: "
                                + (booking.getCar() instanceof Serializable)
                );
                if (booking.getCar().getId().equals(car.getId()))
                    return true;
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
