package ru.sergeyrusakov.testingTask.validation.validators;

import ru.sergeyrusakov.testingTask.validation.annotations.EmployeeNameValidationConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmployeeNameValidator implements ConstraintValidator<EmployeeNameValidationConstraint,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches("^[a-zA-Zа-яА-Я-'/./ ]+$");
    }

}