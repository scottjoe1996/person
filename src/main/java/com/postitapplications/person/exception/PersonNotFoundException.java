package com.postitapplications.person.exception;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
