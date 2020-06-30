package com.postitapplications.person.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.postitapplications.person.document.Person;
import com.postitapplications.person.exceptions.NullOrEmptyException;
import com.postitapplications.person.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

        assertThat(personService.savePerson(expectedPerson)).isEqualTo(expectedPerson);
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

    @Test
    public void getAllPeopleShouldReturnListOfPeopleWhenDatabaseIsNotEmpty() {
        List<Person> people = new ArrayList<>();
        people.add(new Person(UUID.randomUUID(), "John Smith"));
        people.add(new Person(UUID.randomUUID(), "Jane Smith"));

        when(mockPersonRepository.findAll()).thenReturn(people);
        personService = new PersonService(mockPersonRepository);

        assertThat(personService.getAllPeople()).isEqualTo(people);
    }

    @Test
    public void getAllPeopleShouldAnEmptyListOfPeopleWhenDatabaseIsEmpty() {
        when(mockPersonRepository.findAll()).thenReturn(new ArrayList<>());
        personService = new PersonService(mockPersonRepository);

        assertThat(personService.getAllPeople()).isEqualTo(new ArrayList<>());
    }

    @Test
    public void getPersonByIdShouldReturnAPersonWhenPersonExists() {
        UUID savedPersonId = UUID.randomUUID();
        Person savedPerson = new Person(savedPersonId, "John Smith");

        when(mockPersonRepository.findById(savedPersonId)).thenReturn(savedPerson);
        personService = new PersonService(mockPersonRepository);

        assertThat(personService.getPersonById(savedPersonId)).isEqualTo(savedPerson);
    }

    @Test
    public void getPersonByIdShouldReturnNullWhenPersonDoesNotExist() {
        UUID nonExistingPersonID = UUID.randomUUID();

        when(mockPersonRepository.findById(nonExistingPersonID)).thenReturn(null);
        personService = new PersonService(mockPersonRepository);

        assertThat(personService.getPersonById(nonExistingPersonID)).isEqualTo(null);
    }

    @Test
    public void getPersonByIdShouldThrowNullPointerExceptionWhenUsingNullId() {
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            personService.getPersonById(null);
        });

        assertThat(exception.getMessage()).isEqualTo("Id cannot be null");
    }

    @Test
    public void updatePersonShouldReturnUpdateResultWhenUsingAValidPerson() {
        Person updatedPerson = new Person(UUID.randomUUID(), "Jeff Smith");
        UpdateResult mockUpdateResult = Mockito.mock(UpdateResult.class);

        when(mockPersonRepository.update(updatedPerson)).thenReturn(mockUpdateResult);
        personService = new PersonService(mockPersonRepository);

        assertThat(personService.updatePerson(updatedPerson)).isEqualTo(mockUpdateResult);
    }

    @Test
    public void updatePersonShouldReturnNullPointerExceptionWhenPersonIsNull() {
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            personService.updatePerson(null);
        });

        assertThat(exception.getMessage()).isEqualTo("Person cannot be null");
    }

    @Test
    public void updatePersonShouldReturnNullOrEmptyExceptionWhenPersonNameIsNull() {
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            personService.updatePerson(new Person(UUID.randomUUID(), null));
        });

        assertThat(exception.getMessage()).isEqualTo("Person's name cannot be null or empty");
    }

    @Test
    public void updatePersonShouldReturnNullPointerExceptionWhenPersonIdIsNull() {
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            personService.updatePerson(new Person(null, "John Smith"));
        });

        assertThat(exception.getMessage()).isEqualTo("Id cannot be null");
    }

    @Test
    public void deletePersonByIdShouldReturnDeleteResultWhenUsingAValidId() {
        UUID deletedPersonId = UUID.randomUUID();
        DeleteResult mockDeleteResult = Mockito.mock(DeleteResult.class);

        when(mockPersonRepository.removeById(deletedPersonId)).thenReturn(mockDeleteResult);
        personService = new PersonService(mockPersonRepository);

        assertThat(personService.deletePersonById(deletedPersonId)).isEqualTo(mockDeleteResult);
    }

    @Test
    public void deletePersonByIdShouldThrowNullPointerExceptionWhenUsingANullId() {
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            personService.deletePersonById(null);
        });

        assertThat(exception.getMessage()).isEqualTo("Id cannot be null");
    }

}
