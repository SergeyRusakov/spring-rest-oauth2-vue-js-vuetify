package ru.sergeyrusakov.testingTask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<InvalidDataException> invalidEmployeeDataExceptionResponseEntity(){
        return new ResponseEntity<>(new InvalidDataException(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<DataNotFoundException> employeeNotFoundExceptionResponseEntity(){
        return new ResponseEntity<>(new DataNotFoundException(), HttpStatus.NOT_FOUND);
    }
}