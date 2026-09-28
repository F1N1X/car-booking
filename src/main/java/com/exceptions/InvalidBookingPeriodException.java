package com.exceptions;

public class InvalidBookingPeriodException extends RuntimeException {
    public InvalidBookingPeriodException(String message) {
        super(message);
    }
}
