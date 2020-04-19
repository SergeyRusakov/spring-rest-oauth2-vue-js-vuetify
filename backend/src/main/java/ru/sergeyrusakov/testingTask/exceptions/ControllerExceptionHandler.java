package ru.sergeyrusakov.testingTask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidUserDataException.class)
    public ResponseEntity<InvalidUserDataException> invalidUserDataExceptionResponseEntity(){
        return new ResponseEntity<>(new InvalidUserDataException(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserNotFoundException> userNotFoundExceptionResponseEntity(){
        return new ResponseEntity<>(new UserNotFoundException(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}