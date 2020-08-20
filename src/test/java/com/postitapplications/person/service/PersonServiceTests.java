package com.postitapplications.person.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.postitapplications.exception.exceptions.NullOrEmptyException;
import com.postitapplications.person.document.Person;
import com.postitapplications.person.document.Person.Gender;
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
        Person expectedPerson = new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "10/10/2000",
            Gender.MALE);

        when(mockPersonRepository.save(expectedPerson)).thenReturn(expectedPerson);
        personService = new PersonService(mockPersonRepository);

        assertThat(personService.savePerson(expectedPerson)).isEqualTo(expectedPerson);
    }

    @Test
    public void savePersonShouldThrowNullPointerExceptionWhenPersonIsNull() {
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            personService.savePerson(null);
        });

        assertThat(exception.getMessage()).isEqualTo("Person cannot be null");
    }

    @Test
    public void savePersonShouldThrowNullOrEmptyExceptionWhenPersonNameIsNull() {
        Person invalidPerson = new Person(UUID.randomUUID(), null, 1f, 1f, "10/10/2000",
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            personService.savePerson(invalidPerson);
        });

        assertThat(exception.getMessage()).isEqualTo("Person's name cannot be null or empty");
    }

    @Test
    public void savePersonShouldThrowNullOrEmptyExceptionWhenPersonNameIsEmpty() {
        Person invalidPerson = new Person(UUID.randomUUID(), "", 1f, 1f, "10/10/2000", Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            personService.savePerson(invalidPerson);
        });

        assertThat(exception.getMessage()).isEqualTo("Person's name cannot be null or empty");
    }

    @Test
    public void savePersonShouldThrowNullPointerExceptionWhenPersonWeightIsNull() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", null, 1f, "10/10/2000",
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            personService.savePerson(invalidPerson);
        });

        assertThat(exception.getMessage()).isEqualTo("Person's weight cannot be null");
    }

    @Test
    public void savePersonShouldThrowIllegalArgumentExceptionWhenPersonWeightIsZero() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", 0f, 1f, "10/10/2000",
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.savePerson(invalidPerson);
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's weight cannot be less than or equal to zero");
    }

    @Test
    public void savePersonShouldThrowIllegalArgumentExceptionWhenPersonWeightIsNegative() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", -1f, 1f, "10/10/2000",
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.savePerson(invalidPerson);
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's weight cannot be less than or equal to zero");
    }

    @Test
    public void savePersonShouldThrowNullPointerExceptionWhenPersonHeightIsNull() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", 1f, null, "10/10/2000",
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            personService.savePerson(invalidPerson);
        });

        assertThat(exception.getMessage()).isEqualTo("Person's height cannot be null");
    }

    @Test
    public void savePersonShouldThrowIllegalArgumentExceptionWhenPersonHeightIsZero() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", 1f, 0f, "10/10/2000",
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.savePerson(invalidPerson);
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's height cannot be less than or equal to zero");
    }

    @Test
    public void savePersonShouldThrowIllegalArgumentExceptionWhenPersonHeightIsNegative() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", 1f, -1f, "10/10/2000",
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.savePerson(invalidPerson);
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's height cannot be less than or equal to zero");
    }

    @Test
    public void savePersonShouldThrowNullOrEmptyExceptionWhenPersonDateOfBirthIsNull() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", 1f, 1f, null,
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            personService.savePerson(invalidPerson);
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's date of birth cannot be null or empty");
    }

    @Test
    public void savePersonShouldThrowNullOrEmptyExceptionWhenPersonDateOfBirthIsEmpty() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "", Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            personService.savePerson(invalidPerson);
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's date of birth cannot be null or empty");
    }

    @Test
    public void savePersonShouldThrowNullOrEmptyExceptionWhenPersonDateOfBirthIsInWrongFormat() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "10/30/20",
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.savePerson(invalidPerson);
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's date of birth must be in dd/MM/yyyy format");
    }

    @Test
    public void savePersonShouldThrowNullPointerExceptionWhenPersonGenderIsNull() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "10/10/2000",
            null);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            personService.savePerson(invalidPerson);
        });

        assertThat(exception.getMessage()).isEqualTo("Person's gender cannot be null");
    }

    @Test
    public void getAllPeopleShouldReturnListOfPeopleWhenDatabaseIsNotEmpty() {
        List<Person> people = new ArrayList<>();
        people.add(new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "10/10/2000", Gender.MALE));
        people.add(new Person(UUID.randomUUID(), "Jane Smith", 1f, 1f, "10/10/2000", Gender.MALE));

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
        Person savedPerson = new Person(savedPersonId, "John Smith", 1f, 1f, "10/10/2000",
            Gender.MALE);

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
        Person updatedPerson = new Person(UUID.randomUUID(), "Jeff Smith", 1f, 1f, "10/10/2000",
            Gender.MALE);
        UpdateResult mockUpdateResult = Mockito.mock(UpdateResult.class);

        when(mockPersonRepository.update(updatedPerson)).thenReturn(mockUpdateResult);
        personService = new PersonService(mockPersonRepository);

        assertThat(personService.updatePerson(updatedPerson)).isEqualTo(mockUpdateResult);
    }

    @Test
    public void updatePersonShouldThrowNullPointerExceptionWhenPersonIsNull() {
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            personService.updatePerson(null);
        });

        assertThat(exception.getMessage()).isEqualTo("Person cannot be null");
    }

    @Test
    public void updatePersonShouldThrowNullOrEmptyExceptionWhenPersonNameIsNull() {
        Person invalidPerson = new Person(UUID.randomUUID(), null, 1f, 1f, "10/10/2000",
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            personService.updatePerson(invalidPerson);
        });

        assertThat(exception.getMessage()).isEqualTo("Person's name cannot be null or empty");
    }

    @Test
    public void updatePersonShouldThrowNullOrEmptyExceptionWhenPersonNameIsEmpty() {
        Person invalidPerson = new Person(UUID.randomUUID(), "", 1f, 1f, "10/10/2000", Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            personService.updatePerson(invalidPerson);
        });

        assertThat(exception.getMessage()).isEqualTo("Person's name cannot be null or empty");
    }

    @Test
    public void updatePersonShouldThrowIllegalArgumentExceptionWhenPersonWeightIsZero() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", 0f, 1f, "10/10/2000",
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.updatePerson(invalidPerson);
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's weight cannot be less than or equal to zero");
    }

    @Test
    public void updatePersonShouldThrowIllegalArgumentExceptionWhenPersonWeightIsNegative() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", -1f, 1f, "10/10/2000",
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.updatePerson(invalidPerson);
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's weight cannot be less than or equal to zero");
    }

    @Test
    public void updatePersonShouldThrowNullPointerExceptionWhenPersonHeightIsNull() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", 1f, null, "10/10/2000",
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            personService.updatePerson(invalidPerson);
        });

        assertThat(exception.getMessage()).isEqualTo("Person's height cannot be null");
    }

    @Test
    public void updatePersonShouldThrowIllegalArgumentExceptionWhenPersonHeightIsZero() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", 1f, 0f, "10/10/2000",
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.updatePerson(invalidPerson);
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's height cannot be less than or equal to zero");
    }

    @Test
    public void updatePersonShouldThrowIllegalArgumentExceptionWhenPersonHeightIsNegative() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", 1f, -1f, "10/10/2000",
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.updatePerson(invalidPerson);
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's height cannot be less than or equal to zero");
    }

    @Test
    public void updatePersonShouldThrowNullOrEmptyExceptionWhenPersonDateOfBirthIsNull() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", 1f, 1f, null,
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            personService.updatePerson(invalidPerson);
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's date of birth cannot be null or empty");
    }

    @Test
    public void updatePersonShouldThrowNullOrEmptyExceptionWhenPersonDateOfBirthIsEmpty() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "", Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullOrEmptyException.class, () -> {
            personService.updatePerson(invalidPerson);
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's date of birth cannot be null or empty");
    }

    @Test
    public void updatePersonShouldThrowNullOrEmptyExceptionWhenPersonDateOfBirthIsInWrongFormat() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "10/30/20",
            Gender.MALE);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.updatePerson(invalidPerson);
        });

        assertThat(exception.getMessage())
            .isEqualTo("Person's date of birth must be in dd/MM/yyyy format");
    }

    @Test
    public void updatePersonShouldThrowNullPointerExceptionWhenPersonGenderIsNull() {
        Person invalidPerson = new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "10/10/2000",
            null);
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            personService.updatePerson(invalidPerson);
        });

        assertThat(exception.getMessage()).isEqualTo("Person's gender cannot be null");
    }

    @Test
    public void updatePersonShouldReturnNullPointerExceptionWhenPersonIdIsNull() {
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            personService
                .updatePerson(new Person(null, "John Smith", 1f, 1f, "10/10/2000", Gender.MALE));
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
    public void deletePersonByIdShouldThrowNullPointerExceptionWhenIdIsNull() {
        personService = new PersonService(mockPersonRepository);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            personService.deletePersonById(null);
        });

        assertThat(exception.getMessage()).isEqualTo("Id cannot be null");
    }
}
