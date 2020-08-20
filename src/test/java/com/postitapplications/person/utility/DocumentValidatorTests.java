package com.postitapplications.person.utility;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.postitapplications.exception.exceptions.NullOrEmptyException;
import com.postitapplications.person.document.Person;
import com.postitapplications.person.document.Person.Gender;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DocumentValidatorTests {

    @Test
    public void validatePersonShouldThrowNullPointerExceptionWhenPersonIsNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            DocumentValidator.validatePerson(null);
        });

        assertThat(exception.getMessage()).isEqualTo("Person cannot be null");
    }

    @Test
    public void validatePersonShouldThrowNullOrEmptyExceptionWhenPersonNameIsNull() {
        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            DocumentValidator.validatePerson(
                new Person(UUID.randomUUID(), null, 1f, 1f, "10/10/2000", Gender.FEMALE));
        });

        assertThat(exception.getMessage()).isEqualTo("Person's name cannot be null or empty");
    }

    @Test
    public void validatePersonShouldThrowNullOrEmptyExceptionWhenPersonNameIsEmpty() {
        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            DocumentValidator.validatePerson(
                new Person(UUID.randomUUID(), "", 1f, 1f, "10/10/2000", Gender.MALE));
        });

        assertThat(exception.getMessage()).isEqualTo("Person's name cannot be null or empty");
    }

    @Test
    public void validatePersonShouldThrowNullPointerExceptionWhenPersonWeightIsNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            DocumentValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", null, 1f, "10/10/2000", Gender.MALE));
        });

        assertThat(exception.getMessage()).isEqualTo("Person's weight cannot be null");
    }

    @Test
    public void validatePersonShouldThrowIllegalArgumentExceptionWhenPersonWeightIsLessThanZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DocumentValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", -1f, 1f, "10/10/2000", Gender.MALE));
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's weight cannot be less than or equal to zero");
    }

    @Test
    public void validatePersonShouldThrowIllegalArgumentExceptionWhenPersonWeightIsZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DocumentValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", 0f, 1f, "10/10/2000", Gender.MALE));
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's weight cannot be less than or equal to zero");
    }

    @Test
    public void validatePersonShouldThrowNullPointerExceptionWhenPersonHeightIsNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            DocumentValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", 1f, null, "10/10/2000", Gender.MALE));
        });

        assertThat(exception.getMessage()).isEqualTo("Person's height cannot be null");
    }

    @Test
    public void validatePersonShouldThrowIllegalArgumentExceptionWhenPersonHeightIsLessThanZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DocumentValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", 1f, -1f, "10/10/2000", Gender.MALE));
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's height cannot be less than or equal to zero");
    }

    @Test
    public void validatePersonShouldThrowIllegalArgumentExceptionWhenPersonHeightIsZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DocumentValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", 1f, 0f, "10/10/2000", Gender.MALE));
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's height cannot be less than or equal to zero");
    }

    @Test
    public void validatePersonShouldThrowNullOrEmptyExceptionWhenPersonDateOfBirthIsNull() {
        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            DocumentValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", 1f, 1f, null, Gender.MALE));
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's date of birth cannot be null or empty");
    }

    @Test
    public void validatePersonShouldThrowNullOrEmptyExceptionWhenPersonDateOfBirthIsEmpty() {
        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            DocumentValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "", Gender.MALE));
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's date of birth cannot be null or empty");
    }

    @Test
    public void validatePersonShouldThrowIllegalArgumentExceptionWhenPersonDateOfBirthIsOfWrongFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DocumentValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "10/30/12", Gender.MALE));
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's date of birth must be in dd/MM/yyyy format");
    }

    @Test
    public void validatePersonShouldThrowNullPointerExceptionWhenPersonGenderIsNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            DocumentValidator.validatePerson(
                new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "10/10/2000", null));
        });

        assertThat(exception.getMessage()).isEqualTo("Person's gender cannot be null");
    }

    @Test
    public void validatePersonShouldNotThrowAnExceptionWithValidPerson() {
        assertDoesNotThrow(() -> DocumentValidator.validatePerson(
            new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "10/10/2000", Gender.MALE)));
    }

    @Test
    public void validatePersonIdShouldThrowNullPointerExceptionWhenIdIsNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            DocumentValidator.validatePersonId(null);
        });

        assertThat(exception.getMessage()).isEqualTo("Id cannot be null");
    }

    @Test
    public void validatePersonIdShouldNotThrowAnExceptionWithValidId() {
        assertDoesNotThrow(() -> DocumentValidator.validatePersonId(UUID.randomUUID()));
    }
}
