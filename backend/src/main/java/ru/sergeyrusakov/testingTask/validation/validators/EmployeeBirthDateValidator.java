package ru.sergeyrusakov.testingTask.validation.validators;

import ru.sergeyrusakov.testingTask.validation.annotations.EmployeeBirthDateValidationConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;


public class EmployeeBirthDateValidator implements ConstraintValidator<EmployeeBirthDateValidationConstraint, LocalDate> {
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isAfter(LocalDate.parse("1900-01-01"))&&localDate.isBefore(LocalDate.now());
    }
}
