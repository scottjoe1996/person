package com.postitapplications.person.exceptions;

public class NullOrEmptyException extends RuntimeException {

    public NullOrEmptyException(String errorMessage) {
        super(errorMessage);
    }

}
