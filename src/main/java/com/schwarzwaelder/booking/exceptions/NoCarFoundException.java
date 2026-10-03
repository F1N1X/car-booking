package com.schwarzwaelder.booking.exceptions;

public class NoCarFoundException extends RuntimeException {
    public NoCarFoundException(String message) {
        super(message);
    }
}
