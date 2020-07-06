package com.postitapplications.person.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.postitapplications.person.document.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
public class PeopleIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void savePersonShouldReturnPersonSavedOnSuccessfulSave() {
        Person personToSave = new Person(null, "John Smith");

        ResponseEntity<Person> responseEntity = restTemplate.postForEntity("/person", personToSave,
            Person.class);
        Person personSaved = responseEntity.getBody();

        assertThat(personSaved.getName()).isEqualTo(personSaved.getName());
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
    public void savePersonShouldReturnBadRequestStatusCodeWhenPersonNameIsNull() {
        Person personToSave = new Person(null, null);

        ResponseEntity<Person> responseEntity = restTemplate.postForEntity("/person", personToSave,
            Person.class);
        HttpStatus responseStatusCode = responseEntity.getStatusCode();

        assertThat(responseStatusCode).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
