package com.file.storage.exception;

public class InvalidRegistrationRequestException extends RuntimeException {
    public InvalidRegistrationRequestException(String message) {
        super(message);
    }

    public InvalidRegistrationRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
