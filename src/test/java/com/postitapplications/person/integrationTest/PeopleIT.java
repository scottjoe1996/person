package com.postitapplications.person.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.postitapplications.person.document.Person;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
public class PeopleIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @AfterEach
    public void tearDown() {
        mongoTemplate.dropCollection(Person.class);
    }

    @Test
    public void savePersonShouldReturnPersonSavedOnSuccessfulSave() {
        Person personToSave = new Person(null, "John Smith");

        ResponseEntity<Person> responseEntity = restTemplate.postForEntity("/person", personToSave,
            Person.class);
        Person personSaved = responseEntity.getBody();

        assertThat(personSaved.getName()).isEqualTo(personSaved.getName());
    }

    @Test
    public void savePersonShouldAddPersonToPersonDatabase() {
        Person personToSave = new Person(null, "John Smith");

        restTemplate.postForEntity("/person", personToSave, Person.class);
        Person savedPerson = mongoTemplate.findAll(Person.class).get(0);

        assertThat(savedPerson.getName()).isEqualTo(personToSave.getName());
    }

    @Test
    public void savePersonShouldReturnCreatedStatusCodeOnSuccessfulSave() {
        Person personToSave = new Person(null, "John Smith");

        ResponseEntity<Person> responseEntity = restTemplate.postForEntity("/person", personToSave,
            Person.class);
        HttpStatus responseStatusCode = responseEntity.getStatusCode();

        assertThat(responseStatusCode).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void savePersonShouldReturnUnsupportedMediaTypeStatusCodeWhenPersonIsNull() {
        ResponseEntity<Person> responseEntity = restTemplate.postForEntity("/person", null,
            Person.class);
        HttpStatus responseStatusCode = responseEntity.getStatusCode();

        assertThat(responseStatusCode).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void savePersonShouldReturnBadRequestStatusCodeWhenPersonHasInvalidFields() {
        Person personToSave = new Person(null, null);

        ResponseEntity<Person> responseEntity = restTemplate.postForEntity("/person", personToSave,
            Person.class);
        HttpStatus responseStatusCode = responseEntity.getStatusCode();

        assertThat(responseStatusCode).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getPeopleShouldReturnAListOfSavedPeopleOnSuccessfulResponse() {
        Person savedPerson = new Person(UUID.randomUUID(), "John Smith");
        mongoTemplate.save(savedPerson);

        ResponseEntity<List<Person>> responseEntity = restTemplate.exchange("/person",
            HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        List<Person> responseEntityBody = responseEntity.getBody();

        assertThat(responseEntityBody.get(0).getId()).isEqualTo(savedPerson.getId());
        assertThat(responseEntityBody.get(0).getName()).isEqualTo(savedPerson.getName());
    }

    @Test
    public void getPeopleShouldReturnOkStatusCodeOnSuccessfulResponse() {
        ResponseEntity<List<Person>> responseEntity = restTemplate.exchange("/person",
            HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        HttpStatus responseEntityStatusCode = responseEntity.getStatusCode();

        assertThat(responseEntityStatusCode).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getPersonByIdShouldReturnFoundPerson() {
        UUID savedPersonId = UUID.randomUUID();
        Person savedPerson = new Person(savedPersonId, "John Smith");
        mongoTemplate.save(savedPerson);

        ResponseEntity<Person> responseEntity =
            restTemplate.getForEntity("/person/" + savedPersonId.toString(),
            Person.class);
        Person responseEntityBody = responseEntity.getBody();

        assertThat(responseEntityBody.getId()).isEqualTo(savedPerson.getId());
        assertThat(responseEntityBody.getName()).isEqualTo(savedPerson.getName());
    }

    @Test
    public void getPersonByIdShouldReturnOkStatusCodeOnSuccessfulResponse() {
        UUID savedPersonId = UUID.randomUUID();
        Person savedPerson = new Person(savedPersonId, "John Smith");
        mongoTemplate.save(savedPerson);

        ResponseEntity<Person> responseEntity =
            restTemplate.getForEntity("/person/" + savedPersonId.toString(),
                Person.class);
        HttpStatus responseEntityStatusCode = responseEntity.getStatusCode();

        assertThat(responseEntityStatusCode).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getPersonByIdShouldReturnNotFoundStatusCodeOnNonExistingPersonId() {
        Person savedPerson = new Person(UUID.randomUUID(), "John Smith");
        mongoTemplate.save(savedPerson);

        ResponseEntity<Person> responseEntity =
            restTemplate.getForEntity("/person/" + UUID.randomUUID().toString(),
                Person.class);
        HttpStatus responseEntityStatusCode = responseEntity.getStatusCode();

        assertThat(responseEntityStatusCode).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getPersonByIdShouldReturnBadRequestStatusCodeOnInvalidPersonId() {
        ResponseEntity<Person> responseEntity = restTemplate.getForEntity("/person/123456",
                Person.class);
        HttpStatus responseEntityStatusCode = responseEntity.getStatusCode();

        assertThat(responseEntityStatusCode).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void updatePersonShouldReturnOKStatusCodeOnSuccessfulUpdate() {
        UUID savedPersonId = UUID.randomUUID();
        Person savedPerson = new Person(savedPersonId, "John Smith");
        mongoTemplate.save(savedPerson);
        Person updatedPerson = new Person(savedPersonId, "Jeff Smith");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> httpEntity = new HttpEntity<>(updatedPerson, headers);

        ResponseEntity<Person> responseEntity = restTemplate.exchange("/person/",
            HttpMethod.PUT, httpEntity, Person.class);
        HttpStatus responseEntityStatusCode = responseEntity.getStatusCode();

        assertThat(responseEntityStatusCode).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void updatePersonShouldReturnUpdatedPersonOnSuccessfulUpdate() {
        UUID savedPersonId = UUID.randomUUID();
        Person savedPerson = new Person(savedPersonId, "John Smith");
        mongoTemplate.save(savedPerson);
        Person updatedPerson = new Person(savedPersonId, "Jeff Smith");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> httpEntity = new HttpEntity<>(updatedPerson, headers);

        ResponseEntity<Person> responseEntity = restTemplate.exchange("/person/",
            HttpMethod.PUT, httpEntity, Person.class);
        Person responseEntityBody = responseEntity.getBody();

        assertThat(responseEntityBody.getId()).isEqualTo(updatedPerson.getId());
        assertThat(responseEntityBody.getName()).isEqualTo(updatedPerson.getName());
    }

    @Test
    public void updatePersonShouldReturnNotFoundStatusCodeOnPersonWithNonExistingPersonId() {
        UUID savedPersonId = UUID.randomUUID();
        Person savedPerson = new Person(savedPersonId, "John Smith");
        mongoTemplate.save(savedPerson);
        Person updatedPerson = new Person(UUID.randomUUID(), "Jeff Smith");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> httpEntity = new HttpEntity<>(updatedPerson, headers);

        ResponseEntity<Person> responseEntity = restTemplate.exchange("/person/",
            HttpMethod.PUT, httpEntity, Person.class);
        HttpStatus responseEntityStatusCode = responseEntity.getStatusCode();

        assertThat(responseEntityStatusCode).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void updatePersonShouldReturnBadRequestStatusCodeOnPersonWithInvalidFields() {
        UUID savedPersonId = UUID.randomUUID();
        Person savedPerson = new Person(savedPersonId, "John Smith");
        mongoTemplate.save(savedPerson);
        Person updatedPerson = new Person(UUID.randomUUID(), null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> httpEntity = new HttpEntity<>(updatedPerson, headers);

        ResponseEntity<Person> responseEntity = restTemplate.exchange("/person/",
            HttpMethod.PUT, httpEntity, Person.class);
        HttpStatus responseEntityStatusCode = responseEntity.getStatusCode();

        assertThat(responseEntityStatusCode).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void deletePersonByIdShouldReturnOkStatusCodeOnSuccessfulDelete() {
        UUID savedPersonId = UUID.randomUUID();
        Person savedPerson = new Person(savedPersonId, "John Smith");
        mongoTemplate.save(savedPerson);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UUID> responseEntity = restTemplate.exchange("/person/" +
                savedPersonId.toString(), HttpMethod.DELETE, httpEntity, UUID.class);
        HttpStatus responseEntityStatusCode = responseEntity.getStatusCode();

        assertThat(responseEntityStatusCode).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deletePersonByIdShouldReturnDeletedPersonsIdOnSuccessfulDelete() {
        UUID savedPersonId = UUID.randomUUID();
        Person savedPerson = new Person(savedPersonId, "John Smith");
        mongoTemplate.save(savedPerson);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UUID> responseEntity = restTemplate.exchange("/person/" +
            savedPersonId.toString(), HttpMethod.DELETE, httpEntity, UUID.class);
        UUID responseEntityBody = responseEntity.getBody();

        assertThat(responseEntityBody).isEqualTo(savedPersonId);
    }

    @Test
    public void deletePersonByIdShouldReturnNotFoundStatusCodeWithNonExistingPersonId() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("/person/" +
                UUID.randomUUID().toString(), HttpMethod.DELETE, httpEntity, String.class);
        HttpStatus responseEntityStatusCode = responseEntity.getStatusCode();

        assertThat(responseEntityStatusCode).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void deletePersonByIdShouldReturnBadRequestStatusCodeWithInvalidPersonId() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("/person/123456",
            HttpMethod.DELETE, httpEntity, String.class);
        HttpStatus responseEntityStatusCode = responseEntity.getStatusCode();

        assertThat(responseEntityStatusCode).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
