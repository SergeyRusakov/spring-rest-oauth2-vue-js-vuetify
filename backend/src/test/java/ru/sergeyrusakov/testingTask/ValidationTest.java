package ru.sergeyrusakov.testingTask;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sergeyrusakov.testingTask.entities.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ValidationTest {

    private User user;
    private Validator validator;

    public ValidationTest(){
        user = new User();
        user.setName("Sam");
        user.setSurname("Bridges");
        user.setEmail("sambridges@gmail.com");
        user.setBirthDate(LocalDate.parse("1995-10-27"));
        user.setMarried(true);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator=validatorFactory.usingContext().getValidator();
    }

    @Test
    public void correctDataTest1(){
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        assertThat(validates).isEmpty();
    }

    @Test
    public void correctDataTest2(){
        User newUser = new User();
        newUser.setSurname("Sam-John jr.");
        newUser.setName("Daniel");
        newUser.setEmail("daniel1@gmail.ru");
        newUser.setMarried(false);
        newUser.setBirthDate(LocalDate.parse("1900-01-02"));
        Set<ConstraintViolation<User>> validates = validator.validate(newUser);
        assertThat(validates).isEmpty();
    }

    @Test
    public void incorrectBirthDateTestAfter(){
        user.setBirthDate(LocalDate.parse("2100-10-27"));
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        assertThat(validates.size()).isEqualTo(1);
        String error = validates.stream().map(ConstraintViolation::getMessage).findFirst().orElse(null);
        assertThat(error).isEqualTo("Invalid birth date");
        user.setBirthDate(LocalDate.parse("1995-10-27"));
    }

    @Test
    public void incorrectBirthDateTestBefore(){
        user.setBirthDate(LocalDate.parse("1899-10-27"));
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        assertThat(validates.size()).isEqualTo(1);
        String error = validates.stream().map(ConstraintViolation::getMessage).findFirst().orElse(null);
        assertThat(error).isEqualTo("Invalid birth date");
        user.setBirthDate(LocalDate.parse("1995-10-27"));
    }

    @Test
    public void incorrectNameTest(){
        user.setName("Sam2");
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        assertThat(validates.size()).isEqualTo(1);
        String error = validates.stream().map(ConstraintViolation::getMessage).findFirst().orElse(null);
        assertThat(error).isEqualTo("Invalid name");
        user.setName("Sam");
    }

    @Test
    public void invalidSurnameTest(){
        Logger logger = LoggerFactory.getLogger("TEST LOGGER");
        user.setSurname(".Bridges1");
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        assertThat(validates.size()).isEqualTo(1);
        String error = validates.stream().map(ConstraintViolation::getMessage).findFirst().orElse(null);
        assertThat(error).isEqualTo("Invalid surname");
        user.setSurname("Bridges");
    }

    @Test
    public void incorrectEmailTest(){
        user.setEmail("samBridges");
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        assertThat(validates.size()).isEqualTo(1);
        String error = validates.stream().map(ConstraintViolation::getMessage).findFirst().orElse(null);
        assertThat(error).isEqualTo("Invalid email");
        user.setEmail("sambridges@gmail.com");
    }

}
