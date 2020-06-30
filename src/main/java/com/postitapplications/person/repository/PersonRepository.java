package com.postitapplications.person.repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.postitapplications.person.document.Person;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository("MongoDBRepo")
public class PersonRepository implements PersonRepo {

    final MongoTemplate mongoTemplate;

    @Autowired
    public PersonRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Person save(UUID id, Person personToInsert) {
        Person person = new Person(id, personToInsert.getName());
        return mongoTemplate.save(person);
    }

    @Override
    public List<Person> findAll() {
        return mongoTemplate.findAll(Person.class);
    }

    @Override
    public Person findById(UUID id) {
        return mongoTemplate.findById(id, Person.class);
    }

    @Override
    public UpdateResult update(Person person) {
        Update update = new Update();
        update.set("name", person.getName());

        return mongoTemplate.updateFirst(new Query(Criteria.where("id").is(person.getId())), update,
            Person.class);
    }

    @Override
    public DeleteResult removeById(UUID id) {
        return mongoTemplate.remove(new Query(Criteria.where("id").is(id)), Person.class);
    }

}
