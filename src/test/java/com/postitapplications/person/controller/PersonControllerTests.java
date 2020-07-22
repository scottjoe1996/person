package com.postitapplications.person.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.postitapplications.person.document.Person;
import com.postitapplications.person.document.Person.Gender;
import com.postitapplications.person.repository.PersonRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonRepository personRepository;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void savePersonShouldReturnExpectedErrorMessageWhenPersonNameIsNull() throws Exception {
        Person personToSave = new Person(null, null, 1f, 1f, "10/10/2000", Gender.MALE);

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                                       .content(objectMapper.writeValueAsString(personToSave))
                                       .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(
            content().string(containsString("Person's name cannot be null or empty")));
    }

    @Test
    public void savePersonShouldReturnExpectedErrorMessageWhenPersonNameIsEmpty() throws Exception {
        Person personToSave = new Person(null, "", 1f, 1f, "10/10/2000", Gender.MALE);

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                                       .content(objectMapper.writeValueAsString(personToSave))
                                       .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(
            content().string(containsString("Person's name cannot be null or empty")));
    }

    @Test
    public void savePersonShouldReturnExpectedErrorMessageWhenPersonWeightIsNull()
        throws Exception {
        Person personToSave = new Person(null, "John Smith", null, 1f, "10/10/2000", Gender.MALE);

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                                       .content(objectMapper.writeValueAsString(personToSave))
                                       .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(content().string(containsString("Person's weight cannot be null")));
    }

    @Test
    public void savePersonShouldReturnExpectedErrorMessageWhenPersonWeightIsZero()
        throws Exception {
        Person personToSave = new Person(null, "John Smith", 0f, 1f, "10/10/2000", Gender.MALE);

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                                       .content(objectMapper.writeValueAsString(personToSave))
                                       .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(content()
            .string(containsString("Person's weight cannot be less than or equal to " + "zero")));
    }

    @Test
    public void savePersonShouldReturnExpectedErrorMessageWhenPersonWeightIsNegative()
        throws Exception {
        Person personToSave = new Person(null, "John Smith", -1f, 1f, "10/10/2000", Gender.MALE);

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                                       .content(objectMapper.writeValueAsString(personToSave))
                                       .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(content()
            .string(containsString("Person's weight cannot be less than or equal to zero")));
    }

    @Test
    public void savePersonShouldReturnExpectedErrorMessageWhenPersonHeightIsNull()
        throws Exception {
        Person personToSave = new Person(null, "John Smith", 1f, null, "10/10/2000", Gender.MALE);

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                                       .content(objectMapper.writeValueAsString(personToSave))
                                       .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(content().string(containsString("Person's height cannot be null")));
    }

    @Test
    public void savePersonShouldReturnExpectedErrorMessageWhenPersonHeightIsZero()
        throws Exception {
        Person personToSave = new Person(null, "John Smith", 1f, 0f, "10/10/2000", Gender.MALE);

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                                       .content(objectMapper.writeValueAsString(personToSave))
                                       .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(content()
            .string(containsString("Person's height cannot be less than or equal to zero")));
    }

    @Test
    public void savePersonShouldReturnExpectedErrorMessageWhenPersonHeightIsNegative()
        throws Exception {
        Person personToSave = new Person(null, "John Smith", 1f, -1f, "10/10/2000", Gender.MALE);

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                                       .content(objectMapper.writeValueAsString(personToSave))
                                       .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(content()
            .string(containsString("Person's height cannot be less than or equal to zero")));
    }

    @Test
    public void savePersonShouldReturnExpectedErrorMessageWhenPersonDateOfBirthIsNull()
        throws Exception {
        Person personToSave = new Person(null, "John Smith", 1f, 1f, null, Gender.MALE);

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                                       .content(objectMapper.writeValueAsString(personToSave))
                                       .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(
            content().string(containsString("Person's date of birth cannot be null or empty")));
    }

    @Test
    public void savePersonShouldReturnExpectedErrorMessageWhenPersonDateOfBirthIsEmpty()
        throws Exception {
        Person personToSave = new Person(null, "John Smith", 1f, 1f, "", Gender.MALE);

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                                       .content(objectMapper.writeValueAsString(personToSave))
                                       .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(
            content().string(containsString("Person's date of birth cannot be null or empty")));
    }

    @Test
    public void savePersonShouldReturnExpectedErrorMessageWhenPersonDateOfBirthIsInWrongFormat()
        throws Exception {
        Person personToSave = new Person(null, "John Smith", 1f, 1f, "10/30/20", Gender.MALE);

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
                                       .content(objectMapper.writeValueAsString(personToSave))
                                       .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(content()
            .string(containsString("Person's date of birth must be in dd/MM/yyyy format")));
    }

    @Test
    public void getPersonByIdShouldReturnExpectedErrorMessageWhenPersonIsNotFound()
        throws Exception {
        UUID nonExistingPersonId = UUID.randomUUID();

        when(personRepository.findById(nonExistingPersonId)).thenReturn(null);

        mockMvc.perform(
            get("/person/" + nonExistingPersonId).contentType(MediaType.APPLICATION_JSON)
                                                 .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isNotFound()).andExpect(content()
            .string(containsString("Person with id: " + nonExistingPersonId + " was not found")));
    }

    @Test
    public void updatePersonShouldReturnExpectedErrorMessageWhenPersonIsNotFound()
        throws Exception {
        UpdateResult updateResult = Mockito.mock(UpdateResult.class);
        Person personToUpdate = new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "10/10/2000",
            Gender.MALE);

        when(updateResult.getMatchedCount()).thenReturn((long) 0);
        when(personRepository.update(Mockito.any())).thenReturn(updateResult);

        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
                                      .content(objectMapper.writeValueAsString(personToUpdate))
                                      .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isNotFound()).andExpect(content().string(
            containsString("Person with id: " + personToUpdate.getId() + " was not found")));
    }

    @Test
    public void updatePersonShouldReturnExpectedErrorMessageWhenPersonNameIsNull()
        throws Exception {
        Person personToUpdate = new Person(UUID.randomUUID(), null, 1f, 1f, "10/10/2000",
            Gender.MALE);

        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
                                      .content(objectMapper.writeValueAsString(personToUpdate))
                                      .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(
            content().string(containsString("Person's name cannot be null or empty")));
    }

    @Test
    public void updatePersonShouldReturnExpectedErrorMessageWhenPersonNameIsEmpty()
        throws Exception {
        Person personToUpdate = new Person(UUID.randomUUID(), "", 1f, 1f, "10/10/2000",
            Gender.MALE);

        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
                                      .content(objectMapper.writeValueAsString(personToUpdate))
                                      .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(
            content().string(containsString("Person's name cannot be null or empty")));
    }

    @Test
    public void updatePersonShouldReturnExpectedErrorMessageWhenPersonWeightIsNull()
        throws Exception {
        Person personToUpdate = new Person(UUID.randomUUID(), "John Smith", null, 1f, "10/10/2000",
            Gender.MALE);

        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
                                      .content(objectMapper.writeValueAsString(personToUpdate))
                                      .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(content().string(containsString("Person's weight cannot be null")));
    }

    @Test
    public void updatePersonShouldReturnExpectedErrorMessageWhenPersonWeightIsZero()
        throws Exception {
        Person personToUpdate = new Person(UUID.randomUUID(), "John Smith", 0f, 1f, "10/10/2000",
            Gender.MALE);

        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
                                      .content(objectMapper.writeValueAsString(personToUpdate))
                                      .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(content()
            .string(containsString("Person's weight cannot be less than or equal to zero")));
    }

    @Test
    public void updatePersonShouldReturnExpectedErrorMessageWhenPersonWeightIsNegative()
        throws Exception {
        Person personToUpdate = new Person(UUID.randomUUID(), "John Smith", -1f, 1f, "10/10/2000",
            Gender.MALE);

        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
                                      .content(objectMapper.writeValueAsString(personToUpdate))
                                      .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(content()
            .string(containsString("Person's weight cannot be less than or equal to zero")));
    }

    @Test
    public void updatePersonShouldReturnExpectedErrorMessageWhenPersonHeightIsNull()
        throws Exception {
        Person personToUpdate = new Person(UUID.randomUUID(), "John Smith", 1f, null, "10/10/2000",
            Gender.MALE);

        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
                                      .content(objectMapper.writeValueAsString(personToUpdate))
                                      .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(content().string(containsString("Person's height cannot be null")));
    }

    @Test
    public void updatePersonShouldReturnExpectedErrorMessageWhenPersonHeightIsZero()
        throws Exception {
        Person personToUpdate = new Person(UUID.randomUUID(), "John Smith", 1f, 0f, "10/10/2000",
            Gender.MALE);

        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
                                      .content(objectMapper.writeValueAsString(personToUpdate))
                                      .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(content()
            .string(containsString("Person's height cannot be less than or equal to zero")));
    }

    @Test
    public void updatePersonShouldReturnExpectedErrorMessageWhenPersonHeightIsNegative()
        throws Exception {
        Person personToUpdate = new Person(UUID.randomUUID(), "John Smith", 1f, -1f, "10/10/2000",
            Gender.MALE);

        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
                                      .content(objectMapper.writeValueAsString(personToUpdate))
                                      .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(content()
            .string(containsString("Person's height cannot be less than or equal to zero")));
    }

    @Test
    public void updatePersonShouldReturnExpectedErrorMessageWhenPersonDateOfBirthIsNull()
        throws Exception {
        Person personToUpdate = new Person(UUID.randomUUID(), "John Smith", 1f, 1f, null,
            Gender.MALE);

        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
                                      .content(objectMapper.writeValueAsString(personToUpdate))
                                      .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(content()
            .string(containsString("Person's date of birth cannot be null or empty")));
    }

    @Test
    public void updatePersonShouldReturnExpectedErrorMessageWhenPersonDateOfBirthIsEmpty()
        throws Exception {
        Person personToUpdate = new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "",
            Gender.MALE);

        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
                                      .content(objectMapper.writeValueAsString(personToUpdate))
                                      .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(content()
            .string(containsString("Person's date of birth cannot be null or empty")));
    }

    @Test
    public void updatePersonShouldReturnExpectedErrorMessageWhenPersonDateOfBirthIsInWrongFormat()
        throws Exception {
        Person personToUpdate = new Person(UUID.randomUUID(), "John Smith", 1f, 1f, "10/30/20",
            Gender.MALE);

        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
                                      .content(objectMapper.writeValueAsString(personToUpdate))
                                      .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest()).andExpect(content()
            .string(containsString("Person's date of birth must be in dd/MM/yyyy format")));
    }

    @Test
    public void updatePersonShouldReturnExpectedErrorMessageWhenPersonIdIsNull()
        throws Exception {
        Person personToUpdate = new Person(null, "John Smith", 1f, 1f, "10/10/2000", Gender.MALE);

        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
                                      .content(objectMapper.writeValueAsString(personToUpdate))
                                      .accept(MediaType.APPLICATION_JSON)).andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(content().string(containsString("Id cannot be null")));
    }

    @Test
    public void deletePersonByIdShouldReturnExpectedErrorMessageWhenPersonIsNotFound()
        throws Exception {
        DeleteResult deleteResult = Mockito.mock(DeleteResult.class);
        UUID nonExistingPersonId = UUID.randomUUID();

        when(deleteResult.getDeletedCount()).thenReturn((long) 0);
        when(personRepository.removeById(nonExistingPersonId)).thenReturn(deleteResult);

        mockMvc.perform(
            delete("/person/" + nonExistingPersonId).contentType(MediaType.APPLICATION_JSON)
                                                    .accept(MediaType.APPLICATION_JSON))
               .andDo(print()).andExpect(status().isNotFound()).andExpect(content()
            .string(containsString("Person with id: " + nonExistingPersonId + " was not found")));
    }
}
