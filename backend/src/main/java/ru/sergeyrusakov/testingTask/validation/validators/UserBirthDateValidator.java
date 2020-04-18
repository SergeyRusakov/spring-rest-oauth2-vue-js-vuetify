package ru.sergeyrusakov.testingTask.validation.validators;

import ru.sergeyrusakov.testingTask.validation.annotations.UserBirthDateValidationConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;


public class UserBirthDateValidator implements ConstraintValidator<UserBirthDateValidationConstraint, LocalDate> {
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isAfter(LocalDate.parse("1900-01-01"))&&localDate.isBefore(LocalDate.now());
    }
}
