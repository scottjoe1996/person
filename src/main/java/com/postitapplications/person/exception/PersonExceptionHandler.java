package com.postitapplications.person.exception;

import com.postitapplications.exception.exceptions.NullOrEmptyException;
import com.postitapplications.exception.exceptions.PersonNotFoundException;
import com.postitapplications.exception.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.postitapplications.exception.ExceptionResponseBody;

@RestControllerAdvice
public class PersonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<Object> handleBadRequestException(Exception exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionResponseBody exceptionResponseBody = new ExceptionResponseBody(badRequest,
            exception.getMessage());

        return new ResponseEntity<>(exceptionResponseBody, badRequest);
    }

    @ExceptionHandler(value = {PersonNotFoundException.class})
    public ResponseEntity<Object> handlePersonNotFoundException(PersonNotFoundException exception) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ExceptionResponseBody exceptionResponseBody = new ExceptionResponseBody(notFound,
            exception.getMessage());

        return new ResponseEntity<>(exceptionResponseBody, notFound);
    }
}
