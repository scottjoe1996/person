package com.postitapplications.person.controller;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.postitapplications.person.document.Person;
import com.postitapplications.person.exception.PersonNotFoundException;
import com.postitapplications.person.service.PersonService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<Person> savePerson(@RequestBody Person person) {
        Person savedPerson = personService.savePerson(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Person> getPeople() {
        return personService.getAllPeople();
    }

    @GetMapping("{id}")
    public Person getPersonById(@PathVariable("id") UUID id) {
        Person foundPerson = personService.getPersonById(id);

        if (foundPerson == null) {
            throw new PersonNotFoundException(
                String.format("Person with id: %s was not found", id)
            );
        }

        return foundPerson;
    }

    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        UpdateResult updateResult = personService.updatePerson(person);

        if (updateResult.getMatchedCount() == 0) {
            throw new PersonNotFoundException(
                String.format("Person with id: %s was not found", person.getId())
            );
        }

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UUID> deletePersonById(@PathVariable("id") UUID id) {
        DeleteResult deleteResult = personService.deletePersonById(id);

        if (deleteResult.getDeletedCount() == 0) {
            throw new PersonNotFoundException(
                String.format("Person with id: %s was not found", id)
            );
        }

        return  new ResponseEntity<>(id, HttpStatus.OK);
    }

}
