package com.postitapplications.person.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "people")
public class Person {

    @Id
    private final UUID id;
    @NotBlank
    private final String name;
    @NotBlank
    private final Float weight;
    @NotBlank
    private final Float height;
    @NotBlank
    private final String dateOfBirth;
    @NotBlank
    private final Gender gender;

    public Person(@JsonProperty("id") UUID id, @JsonProperty("name") String name,
        @JsonProperty("weight") Float weight, @JsonProperty("height") Float height,
        @JsonProperty("dateOfBirth") String dateOfBirth, @JsonProperty("gender") Gender gender) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getWeight() {
        return weight;
    }

    public Float getHeight() {
        return height;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public enum Gender {
        MALE, FEMALE
    }
}
