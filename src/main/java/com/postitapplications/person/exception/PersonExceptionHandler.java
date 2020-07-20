package com.postitapplications.person.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class PersonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NullOrEmptyException.class, NullPointerException.class,
        IllegalArgumentException.class})
    public ResponseEntity<Object> handleBadRequestException(Exception exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        body.put("error", badRequest);
        body.put("message", exception.getMessage());
        body.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(body, badRequest);
    }

    @ExceptionHandler(value = {PersonNotFoundException.class})
    public ResponseEntity<Object> handlePersonNotFoundException(PersonNotFoundException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        HttpStatus notFound = HttpStatus.NOT_FOUND;

        body.put("error", notFound);
        body.put("message", exception.getMessage());
        body.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(body, notFound);
    }
}
