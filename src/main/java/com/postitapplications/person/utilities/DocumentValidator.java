package com.postitapplications.person.utilities;

import com.postitapplications.person.document.Person;
import com.postitapplications.person.exception.NullOrEmptyException;
import java.util.UUID;

public class DocumentValidator {

    public static void validatePerson(Person person) {
        if (person == null) {
            throw new NullPointerException("Person cannot be null");
        }

        if (person.getName() == null || person.getName().isEmpty()) {
            throw new NullOrEmptyException("Person's name cannot be null or empty");
        }
    }

    public static void validateId(UUID id) {
        if (id == null) {
            throw new NullPointerException("Id cannot be null");
        }
    }
}
