package com.schwarzwaelder.booking.exceptions;

public class EmptyBookingException extends RuntimeException {
    public EmptyBookingException(String message) {
        super(message);
    }
}
