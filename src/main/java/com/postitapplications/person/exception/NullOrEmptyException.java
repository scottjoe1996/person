package com.postitapplications.person.exception;

public class NullOrEmptyException extends RuntimeException {

    public NullOrEmptyException(String errorMessage) {
        super(errorMessage);
    }
}
