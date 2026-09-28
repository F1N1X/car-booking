package com.exceptions;

public class NoCarFoundException extends RuntimeException {
    public NoCarFoundException(String message) {
        super(message);
    }
}
