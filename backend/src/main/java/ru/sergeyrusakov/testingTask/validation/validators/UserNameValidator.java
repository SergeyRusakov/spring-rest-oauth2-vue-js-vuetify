package ru.sergeyrusakov.testingTask.validation.validators;

import ru.sergeyrusakov.testingTask.validation.annotations.UserNameValidationConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameValidator implements ConstraintValidator<UserNameValidationConstraint,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches("^[a-zA-Zа-яА-Я-'/./ ]+$");
    }

}