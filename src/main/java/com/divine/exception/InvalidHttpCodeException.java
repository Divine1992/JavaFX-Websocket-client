package com.divine.exception;

public class InvalidHttpCodeException extends RuntimeException {

    public InvalidHttpCodeException() {
    }

    public InvalidHttpCodeException(String message) {
        super(message);
    }
}
