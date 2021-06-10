package com.example.homework.exception;

public class InvalidTypeException extends RuntimeException {
    public InvalidTypeException() {
    }

    public InvalidTypeException(String message) {
        super(message);
    }
}
