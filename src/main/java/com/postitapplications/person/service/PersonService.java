package com.postitapplications.person.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.postitapplications.person.document.Person;
import com.postitapplications.person.repository.PersonRepo;
import com.postitapplications.person.utilities.DocumentValidator;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepo personRepo;

    @Autowired
    public PersonService(@Qualifier("MongoDBRepo") PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    public Person savePerson(Person person) {
        DocumentValidator.validatePerson(person);
        return personRepo.save(person);
    }

    public List<Person> getAllPeople() {
        return personRepo.findAll();
    }

    public Person getPersonById(UUID id) {
        DocumentValidator.validateId(id);
        return personRepo.findById(id);
    }

    public UpdateResult updatePerson(Person person) {
        DocumentValidator.validatePerson(person);
        DocumentValidator.validateId(person.getId());
        return personRepo.update(person);
    }

    public DeleteResult deletePersonById(UUID id) {
        DocumentValidator.validateId(id);
        return personRepo.removeById(id);
    }

}