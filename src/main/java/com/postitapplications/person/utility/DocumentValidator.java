package com.postitapplications.person.utility;

import com.postitapplications.person.document.Person;
import com.postitapplications.person.document.Person.Gender;
import com.postitapplications.person.exception.NullOrEmptyException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class DocumentValidator {

    private final static String DATE_FORMAT = "dd/MM/yyyy";

    public static void validatePerson(Person person) {
        if (person == null) {
            throw new NullPointerException("Person cannot be null");
        }

        validateName(person.getName());
        validateWeight(person.getWeight());
        validateHeight(person.getHeight());
        validateDateOfBirth(person.getDateOfBirth());
        validateGender(person.getGender());
    }

    private static void validateName(String personName) {
        if (personName == null || personName.isEmpty()) {
            throw new NullOrEmptyException("Person's name cannot be null or empty");
        }
    }

    private static void validateWeight(Float personWeight) {
        if (personWeight == null) {
            throw new NullPointerException("Person's weight cannot be null");
        }

        if (personWeight <= 0) {
            throw new IllegalArgumentException(
                "Person's weight cannot be less than or equal to zero");
        }
    }

    private static void validateHeight(Float personHeight) {
        if (personHeight == null) {
            throw new NullPointerException("Person's height cannot be null");
        }

        if (personHeight <= 0) {
            throw new IllegalArgumentException(
                "Person's height cannot be less than or equal to zero");
        }
    }

    private static void validateDateOfBirth(String dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isEmpty()) {
            throw new NullOrEmptyException("Person's date of birth cannot be null or empty");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        simpleDateFormat.setLenient(false);

        try {
            simpleDateFormat.parse(dateOfBirth);
        } catch (ParseException exception) {
            throw new IllegalArgumentException(
                "Person's date of birth must be in dd/MM/yyyy format");
        }
    }

    private static void validateGender(Gender gender) {
        if (gender == null) {
            throw new NullPointerException("Person's gender cannot be null");
        }
    }

    public static void validatePersonId(UUID id) {
        if (id == null) {
            throw new NullPointerException("Id cannot be null");
        }
    }
}
