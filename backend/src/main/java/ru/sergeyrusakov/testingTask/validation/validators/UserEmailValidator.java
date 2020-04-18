package ru.sergeyrusakov.testingTask.validation.validators;

import ru.sergeyrusakov.testingTask.validation.annotations.UserEmailValidationConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserEmailValidator implements ConstraintValidator<UserEmailValidationConstraint,String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email.matches("[a-zA-Z_-[0-9]]+@[a-zA-Z_-[0-9]]+\\..+");
    }
}
