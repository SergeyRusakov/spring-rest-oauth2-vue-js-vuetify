package ru.sergeyrusakov.testingTask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sergeyrusakov.testingTask.controllers.DataValidationUtil;
import ru.sergeyrusakov.testingTask.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;

@SpringBootTest
public class ValidationTest {
    @Autowired
    private UserRepository repository;

    @Test
    public void dateValidationTest1(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(1995,9,27);
        assertThat(DataValidationUtil.birthDateIsValid(calendar.getTime())).isTrue();
    }

    @Test
    public void dateValidationTest2(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(1900,2,1);
        System.out.println(calendar.getTime());
        assertThat(DataValidationUtil.birthDateIsValid(calendar.getTime())).isTrue();
    }

    @Test
    public void dateValidationTest3(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(1899,1,1);
        assertThat(DataValidationUtil.birthDateIsValid(calendar.getTime())).isFalse();
    }

    @Test
    public void dateValidationTest4(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2100,1,1);
        assertThat(DataValidationUtil.birthDateIsValid(calendar.getTime())).isFalse();
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
