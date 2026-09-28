package com.exceptions;

public class NoUserBookedCarException extends RuntimeException {
    public NoUserBookedCarException(String message) {
        super(message);
    }
}
