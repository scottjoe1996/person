package com.postitapplications.person.utility;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.postitapplications.exception.exceptions.BusinessLogicException;
import com.postitapplications.exception.exceptions.NullOrEmptyException;
import com.postitapplications.exception.exceptions.ValidationException;
import com.postitapplications.person.document.Person;
import com.postitapplications.person.document.Person.Gender;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonValidatorTests {

    @Test
    public void validatePersonShouldThrowValidationExceptionWhenPersonIsNull() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            PersonValidator.validatePerson(null);
        });

        assertThat(exception.getMessage()).isEqualTo("Person cannot be null");
    }

    @Test
    public void validatePersonShouldThrowNullOrEmptyExceptionWhenPersonNameIsNull() {
        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            PersonValidator.validatePerson(
                new Person(UUID.randomUUID(), null, 1f, 1f, "10/10/2000", Gender.FEMALE));
        });

        assertThat(exception.getMessage()).isEqualTo("Person's name cannot be null or empty");
    }

    @Test
    public void validatePersonShouldThrowNullOrEmptyExceptionWhenPersonNameIsEmpty() {
        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            PersonValidator.validatePerson(
                new Person(UUID.randomUUID(), "", 1f, 1f, "10/10/2000", Gender.MALE));
        });

        assertThat(exception.getMessage()).isEqualTo("Person's name cannot be null or empty");
    }

    @Test
    public void validatePersonShouldThrowValidationExceptionWhenPersonWeightIsNull() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            PersonValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", null, 1f, "10/10/2000", Gender.MALE));
        });

        assertThat(exception.getMessage()).isEqualTo("Person's weight cannot be null");
    }

    @Test
    public void validatePersonShouldThrowIllegalArgumentExceptionWhenPersonWeightIsLessThanZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PersonValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", -1f, 1f, "10/10/2000", Gender.MALE));
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's weight cannot be less than or equal to zero");
    }

    @Test
    public void validatePersonShouldThrowIllegalArgumentExceptionWhenPersonWeightIsZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PersonValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", 0f, 1f, "10/10/2000", Gender.MALE));
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's weight cannot be less than or equal to zero");
    }

    @Test
    public void validatePersonShouldThrowValidationExceptionWhenPersonHeightIsNull() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            PersonValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", 1f, null, "10/10/2000", Gender.MALE));
        });

        assertThat(exception.getMessage()).isEqualTo("Person's height cannot be null");
    }

    @Test
    public void validatePersonShouldThrowBusinessLogicExceptionWhenPersonHeightIsLessThanZero() {
        Exception exception = assertThrows(BusinessLogicException.class, () -> {
            PersonValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", 1f, -1f, "10/10/2000", Gender.MALE));
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's height cannot be less than or equal to zero");
    }

    @Test
    public void validatePersonShouldThrowBusinessLogicExceptionWhenPersonHeightIsZero() {
        Exception exception = assertThrows(BusinessLogicException.class, () -> {
            PersonValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", 1f, 0f, "10/10/2000", Gender.MALE));
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's height cannot be less than or equal to zero");
    }

    @Test
    public void validatePersonShouldThrowNullOrEmptyExceptionWhenPersonDateOfBirthIsNull() {
        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            PersonValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", 1f, 1f, null, Gender.MALE));
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's date of birth cannot be null or empty");
    }

    @Test
    public void validatePersonShouldThrowNullOrEmptyExceptionWhenPersonDateOfBirthIsEmpty() {
        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            PersonValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "", Gender.MALE));
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's date of birth cannot be null or empty");
    }

    @Test
    public void validatePersonShouldThrowBusinessLogicExceptionWhenPersonDateOfBirthIsOfWrongFormat() {
        Exception exception = assertThrows(BusinessLogicException.class, () -> {
            PersonValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "10/30/12", Gender.MALE));
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's date of birth must be in dd/MM/yyyy format");
    }

    @Test
    public void validatePersonShouldThrowValidationExceptionWhenPersonGenderIsNull() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            PersonValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "10/10/2000", null));
        });

        assertThat(exception.getMessage()).isEqualTo("Person's gender cannot be null");
    }

    @Test
    public void validatePersonShouldNotThrowAnExceptionWithValidPerson() {
        assertDoesNotThrow(() -> PersonValidator.validatePerson(
            new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "10/10/2000", Gender.MALE)));
    }

    @Test
    public void validatePersonIdShouldThrowValidationExceptionWhenIdIsNull() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            PersonValidator.validatePersonId(null);
        });

        assertThat(exception.getMessage()).isEqualTo("Id cannot be null");
    }

    @Test
    public void validatePersonIdShouldNotThrowAnExceptionWithValidId() {
        assertDoesNotThrow(() -> PersonValidator.validatePersonId(UUID.randomUUID()));
    }
}
