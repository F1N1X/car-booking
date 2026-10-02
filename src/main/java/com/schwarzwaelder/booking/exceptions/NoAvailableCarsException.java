package com.schwarzwaelder.booking.exceptions;

public class NoAvailableCarsException extends RuntimeException {
    public NoAvailableCarsException(String message) {
        super(message);
    }
}
