package ru.sergeyrusakov.testingTask;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sergeyrusakov.testingTask.entities.Employee;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ValidationTest {

    private Employee employee;
    private Validator validator;

    public ValidationTest(){
        employee = new Employee();
        employee.setName("Sam");
        employee.setSurname("Bridges");
        employee.setEmail("sambridges@gmail.com");
        employee.setBirthDate(LocalDate.parse("1995-10-27"));
        employee.setMarried(true);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator=validatorFactory.usingContext().getValidator();
    }

    @Test
    public void correctDataTest1(){
        Set<ConstraintViolation<Employee>> validates = validator.validate(employee);
        assertThat(validates).isEmpty();
    }

    @Test
    public void correctDataTest2(){
        Employee newEmployee = new Employee();
        newEmployee.setSurname("Sam-John jr.");
        newEmployee.setName("Daniel");
        newEmployee.setEmail("daniel1@gmail.ru");
        newEmployee.setMarried(false);
        newEmployee.setBirthDate(LocalDate.parse("1900-01-02"));
        Set<ConstraintViolation<Employee>> validates = validator.validate(newEmployee);
        assertThat(validates).isEmpty();
    }

    @Test
    public void incorrectBirthDateTestAfter(){
        employee.setBirthDate(LocalDate.parse("2100-10-27"));
        Set<ConstraintViolation<Employee>> validates = validator.validate(employee);
        assertThat(validates.size()).isEqualTo(1);
        String error = validates.stream().map(ConstraintViolation::getMessage).findFirst().orElse(null);
        assertThat(error).isEqualTo("Invalid birth date");
        employee.setBirthDate(LocalDate.parse("1995-10-27"));
    }

    @Test
    public void incorrectBirthDateTestBefore(){
        employee.setBirthDate(LocalDate.parse("1899-10-27"));
        Set<ConstraintViolation<Employee>> validates = validator.validate(employee);
        assertThat(validates.size()).isEqualTo(1);
        String error = validates.stream().map(ConstraintViolation::getMessage).findFirst().orElse(null);
        assertThat(error).isEqualTo("Invalid birth date");
        employee.setBirthDate(LocalDate.parse("1995-10-27"));
    }

    @Test
    public void incorrectNameTest(){
        employee.setName("Sam2");
        Set<ConstraintViolation<Employee>> validates = validator.validate(employee);
        assertThat(validates.size()).isEqualTo(1);
        String error = validates.stream().map(ConstraintViolation::getMessage).findFirst().orElse(null);
        assertThat(error).isEqualTo("Invalid name");
        employee.setName("Sam");
    }

    @Test
    public void invalidSurnameTest(){
        Logger logger = LoggerFactory.getLogger("TEST LOGGER");
        employee.setSurname(".Bridges1");
        Set<ConstraintViolation<Employee>> validates = validator.validate(employee);
        assertThat(validates.size()).isEqualTo(1);
        String error = validates.stream().map(ConstraintViolation::getMessage).findFirst().orElse(null);
        assertThat(error).isEqualTo("Invalid surname");
        employee.setSurname("Bridges");
    }

    @Test
    public void incorrectEmailTest(){
        employee.setEmail("samBridges");
        Set<ConstraintViolation<Employee>> validates = validator.validate(employee);
        assertThat(validates.size()).isEqualTo(1);
        String error = validates.stream().map(ConstraintViolation::getMessage).findFirst().orElse(null);
        assertThat(error).isEqualTo("Invalid email");
        employee.setEmail("sambridges@gmail.com");
    }

}
