package com.postitapplications.person.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.postitapplications.person.document.Person;
import com.postitapplications.person.document.Person.Gender;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class PersonRepositoryTests {

    @Autowired
    private MongoTemplate mongoTemplate;
    private PersonRepository personRepository;

    @BeforeEach
    public void setUp() {
        mongoTemplate
            .save(new Person(UUID.randomUUID(), "John Smith", 1, 1, "10/10/2000", Gender.MALE));
        personRepository = new PersonRepository(mongoTemplate);
    }

    @AfterEach
    public void tearDown() {
        mongoTemplate.dropCollection(Person.class);
    }

    @Test
    public void findByIdShouldReturnExpectedPersonWithCorrectId() {
        UUID savedPersonId = mongoTemplate.findAll(Person.class).get(0).getId();
        Person personFound = personRepository.findById(savedPersonId);

        assertThat(personFound.getName()).isEqualTo("John Smith");
        assertThat(personFound.getWeight()).isEqualTo(1);
        assertThat(personFound.getHeight()).isEqualTo(1);
        assertThat(personFound.getDateOfBirth()).isEqualTo("10/10/2000");
        assertThat(personFound.getGender()).isEqualTo(Gender.MALE);
    }

    @Test
    public void findByIdShouldReturnNullWithInvalidId() {
        assertThat(personRepository.findById(UUID.randomUUID())).isEqualTo(null);
    }

    @Test
    public void removeByIdShouldRemoveSavedPerson() {
        UUID savedPersonId = mongoTemplate.findAll(Person.class).get(0).getId();

        assertThat(personRepository.findAll().size()).isEqualTo(1);
        personRepository.removeById(savedPersonId);

        assertThat(personRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void removeByIdShouldReturnDeletedCount0WithInvalidId() {
        assertThat(personRepository.removeById(UUID.randomUUID()).getDeletedCount()).isEqualTo(0);
    }

    @Test
    public void updateShouldUpdatePersonWithNewFields() {
        UUID savedPersonId = mongoTemplate.findAll(Person.class).get(0).getId();
        Person updatedPerson = new Person(savedPersonId, "Joanne Smith", 2, 2, "10/10/2010",
            Gender.FEMALE);

        personRepository.update(updatedPerson);
        Person personFound = mongoTemplate.findById(savedPersonId, Person.class);

        assertThat(personFound.getName()).isEqualTo("Joanne Smith");
        assertThat(personFound.getWeight()).isEqualTo(2);
        assertThat(personFound.getHeight()).isEqualTo(2);
        assertThat(personFound.getDateOfBirth()).isEqualTo("10/10/2010");
        assertThat(personFound.getGender()).isEqualTo(Gender.FEMALE);
    }

    @Test
    public void updateShouldReturnMatchedCount0WithInvalidId() {
        Person nonExistingPerson = new Person(UUID.randomUUID(), "Jeff Smith", 1, 1, "10/10/2000",
            Gender.MALE);

        assertThat(personRepository.update(nonExistingPerson).getMatchedCount()).isEqualTo(0);
    }

    @Test
    public void findAllShouldReturnAListOfOnePerson() {
        assertThat(personRepository.findAll().size()).isEqualTo(1);
        assertThat(personRepository.findAll().get(0).getClass()).isEqualTo(Person.class);
    }

    @Test
    public void findAllShouldReturnAnEmptyListIfNoPeopleExist() {
        mongoTemplate.dropCollection(Person.class);

        assertThat(personRepository.findAll()).isEqualTo(new ArrayList<Person>());
    }

    @Test
    public void saveShouldAddAPersonToThePersonDatabase() {
        personRepository.save(new Person(null, "Jeff Smith", 1, 1, "10/10/2000", Gender.MALE));

        assertThat(mongoTemplate.findAll(Person.class).size()).isEqualTo(2);
    }

    @Test
    public void saveShouldAddAPersonToThePersonDatabaseWithAGeneratedUUID() {
        mongoTemplate.dropCollection(Person.class);

        personRepository.save(new Person(null, "Jeff Smith", 1, 1, "10/10/2000", Gender.MALE));

        assertThat(mongoTemplate.findAll(Person.class).get(0).getId()).isNotNull();
    }

    @Test
    public void saveShouldAddAPersonToThePersonDatabaseWithTheExpectedFields() {
        mongoTemplate.dropCollection(Person.class);

        personRepository.save(new Person(null, "Jeff Smith", 1, 1, "10/10/2000", Gender.MALE));
        Person savedPerson = mongoTemplate.findAll(Person.class).get(0);

        assertThat(savedPerson.getName()).isEqualTo("Jeff Smith");
        assertThat(savedPerson.getWeight()).isEqualTo(1);
        assertThat(savedPerson.getHeight()).isEqualTo(1);
        assertThat(savedPerson.getDateOfBirth()).isEqualTo("10/10/2000");
        assertThat(savedPerson.getGender()).isEqualTo(Gender.MALE);
    }

    @Test
    public void saveShouldAddAPersonToThePersonDatabaseWithTheExpectedUUIDWhenSpecified() {
        mongoTemplate.dropCollection(Person.class);
        UUID specifiedUUID = UUID.randomUUID();

        personRepository
            .save(specifiedUUID, new Person(null, "Jeff Smith", 1, 1, "10/10/2000", Gender.MALE));

        assertThat(mongoTemplate.findAll(Person.class).get(0).getId()).isEqualTo(specifiedUUID);
    }

    @Test
    public void saveShouldThrowExceptionWhenUsingNullId() {
        Exception exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            personRepository
                .save(null, new Person(null, "Jeff Smith", 1, 1, "10/10/2000", Gender.MALE));
        });

        assertThat(exception.getMessage()).contains("Cannot autogenerate id");
    }
}
