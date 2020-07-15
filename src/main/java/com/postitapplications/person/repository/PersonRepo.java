package com.postitapplications.person.repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.postitapplications.person.document.Person;
import java.util.List;
import java.util.UUID;

public interface PersonRepo {

    Person save(UUID id, Person person);

    default Person save(Person person) {
        UUID id = UUID.randomUUID();
        return save(id, person);
    }

    List<Person> findAll();

    Person findById(UUID id);

    UpdateResult update(Person person);

    DeleteResult removeById(UUID id);
}