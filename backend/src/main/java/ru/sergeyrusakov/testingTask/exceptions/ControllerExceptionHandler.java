package ru.sergeyrusakov.testingTask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidEmployeeDataException.class)
    public ResponseEntity<InvalidEmployeeDataException> invalidEmployeeDataExceptionResponseEntity(){
        return new ResponseEntity<>(new InvalidEmployeeDataException(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EmplyeeNotFoundException.class)
    public ResponseEntity<EmplyeeNotFoundException> employeeNotFoundExceptionResponseEntity(){
        return new ResponseEntity<>(new EmplyeeNotFoundException(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}