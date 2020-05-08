package ru.sergeyrusakov.testingTask.validation.validators;

import ru.sergeyrusakov.testingTask.validation.annotations.EmployeeNameValidationConstraint;
import ru.sergeyrusakov.testingTask.validation.annotations.PositionNameValidationConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PositionNameValidator implements ConstraintValidator<PositionNameValidationConstraint,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches("^[1-9a-zA-Zа-яА-Я-'/./ ]+$");
    }

}
