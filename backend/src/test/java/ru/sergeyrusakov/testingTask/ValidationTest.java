package ru.sergeyrusakov.testingTask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sergeyrusakov.testingTask.controllers.DataValidationUtil;
import ru.sergeyrusakov.testingTask.repositories.UserRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ValidationTest {
    @Autowired
    private UserRepository repository;

    @Test
    public void dateValidationTest1(){
        assertThat(DataValidationUtil.birthDateIsValid(LocalDate.parse("1995-09-27"))).isTrue();
    }

    @Test
    public void dateValidationTest2(){
        assertThat(DataValidationUtil.birthDateIsValid(LocalDate.parse("1900-02-01"))).isTrue();
    }

    @Test
    public void dateValidationTest3(){
        assertThat(DataValidationUtil.birthDateIsValid(LocalDate.parse("1899-01-01"))).isFalse();
    }

    @Test
    public void dateValidationTest4(){
        assertThat(DataValidationUtil.birthDateIsValid(LocalDate.parse("2100-01-01"))).isFalse();
    }


    @Test
    public void nameValidationTest1(){
        assertThat(DataValidationUtil.nameIsValid("Sergey")).isTrue();
    }
    @Test
    public void nameValidationTest2(){
        assertThat(DataValidationUtil.nameIsValid("Сергей")).isTrue();
    }
    @Test
    public void nameValidationTest3(){
        assertThat(DataValidationUtil.nameIsValid("Sergey1")).isFalse();
    }
    @Test
    public void nameValidationTest4(){
        assertThat(DataValidationUtil.nameIsValid("S.ergey")).isTrue();
    }
    @Test
    public void nameValidationTest5(){
        assertThat(DataValidationUtil.nameIsValid("S.ergeyasdasdasdasdaasdasdasdasd")).isFalse();
    }

    @Test
    public void emailValidationTest1(){
        assertThat(DataValidationUtil.emailIsValid("sergeyrusakov1@yandex.ru")).isTrue();
    }
    @Test
    public void emailValidationTest2(){
        assertThat(DataValidationUtil.emailIsValid("sergeyrusakov@yandex1.ru")).isTrue();
    }
    @Test
    public void emailValidationTest3(){
        assertThat(DataValidationUtil.emailIsValid("sergeyrusakov1@yandex")).isFalse();
    }
    @Test
    public void emailValidationTest4(){
        assertThat(DataValidationUtil.emailIsValid("sergeyrusakov1yandex.ru")).isFalse();
    }
    @Test
    public void emailValidationTest5(){
        assertThat(DataValidationUtil.emailIsValid("sergeyrusakovyandex")).isFalse();
    }


}
