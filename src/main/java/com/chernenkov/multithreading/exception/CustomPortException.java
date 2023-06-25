package com.chernenkov.multithreading.exception;

public class CustomPortException extends Exception {
    public CustomPortException() {
        super();
    }

    public CustomPortException(String message) {
        super(message);
    }

    public CustomPortException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomPortException(Throwable cause) {
        super(cause);
    }
}
