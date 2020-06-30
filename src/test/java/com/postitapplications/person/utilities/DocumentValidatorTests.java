package com.postitapplications.person.utilities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.postitapplications.person.document.Person;
import com.postitapplications.person.exceptions.NullOrEmptyException;
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
            DocumentValidator.validatePerson(new Person(UUID.randomUUID(), null));
        });

        assertThat(exception.getMessage()).isEqualTo("Person's name cannot be null or empty");
    }

    @Test
    public void validatePersonShouldThrowNullOrEmptyExceptionWhenPersonNameIsEmpty() {
        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            DocumentValidator.validatePerson(new Person(UUID.randomUUID(), ""));
        });

        assertThat(exception.getMessage()).isEqualTo("Person's name cannot be null or empty");
    }

    @Test
    public void validatePersonShouldNotThrowAnExceptionWithValidPerson() {
        assertDoesNotThrow(() -> DocumentValidator.validatePerson(new Person(UUID.randomUUID(),
            "John Smith")));
    }

    @Test
    public void validateIdShouldThrowNullPointerExceptionWhenIdIsNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            DocumentValidator.validateId(null);
        });

        assertThat(exception.getMessage()).isEqualTo("Id cannot be null");
    }

    @Test
    public void validateIdShouldNotThrowAnExceptionWithValidId() {
        assertDoesNotThrow(() -> DocumentValidator.validateId(UUID.randomUUID()));
    }

}
