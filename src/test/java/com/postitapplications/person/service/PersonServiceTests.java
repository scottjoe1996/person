package com.postitapplications.person.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.postitapplications.person.document.Person;
import com.postitapplications.person.exceptions.NullOrEmptyException;
import com.postitapplications.person.repository.PersonRepository;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
public class PersonServiceTests {

    private PersonService personService;

    @MockBean
    private PersonRepository mockPersonRepository;

    @Test
    public void savePersonShouldReturnSavedPersonOnSuccessfulSave() {
        Person expectedPerson = new Person(UUID.randomUUID(), "John Smith");
        when(mockPersonRepository.save(expectedPerson)).thenReturn(expectedPerson);
        personService = new PersonService(mockPersonRepository);

        Person actualPerson = personService.savePerson(expectedPerson);

        assertThat(actualPerson).isEqualTo(expectedPerson);
    }

    @Test
    public void savePersonShouldThrowExceptionWhenSavingAPersonWithANullName() {
        Person invalidPerson = new Person(UUID.randomUUID(), null);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            personService.savePerson(invalidPerson);
        });

        assertThat(exception.getMessage()).isEqualTo("Person's name cannot be null or empty");
    }

    @Test
    public void savePersonShouldThrowExceptionWhenSavingAPersonWithAnEmptyName() {
        Person invalidPerson = new Person(UUID.randomUUID(), "");
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            personService.savePerson(invalidPerson);
        });

        assertThat(exception.getMessage()).isEqualTo("Person's name cannot be null or empty");
    }

    @Test
    public void savePersonShouldThrowExceptionWhenSavingANullPerson() {
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            personService.savePerson(null);
        });

        assertThat(exception.getMessage()).isEqualTo("Person cannot be null");
    }

}
