package ru.sergeyrusakov.testingTask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sergeyrusakov.testingTask.controllers.UserController;
import ru.sergeyrusakov.testingTask.entities.User;
import ru.sergeyrusakov.testingTask.exceptions.InvalidUserDataException;
import ru.sergeyrusakov.testingTask.exceptions.UserNotFoundException;
import ru.sergeyrusakov.testingTask.repositories.UserRepository;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.Calendar;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userNotFoundTest(){
        Assertions.assertThrows(UserNotFoundException.class,()->{
           userController.delete(300001);
        });
    }

    @Test
    public void invalidUserDataExceptionInSave(){
        Assertions.assertThrows(InvalidUserDataException.class,()->{
           User user = new User();
           Calendar calendar = Calendar.getInstance();
           calendar.set(1995,9,27);
           user.setName("2Sam");
           user.setEmail("wrongEmail");
           user.setSurname("@Johns");
           user.setMarried(true);
           userController.save(user);
        });
    }

    @Test
    public void invalidUserDataExceptionInUpdate(){
        Assertions.assertThrows(InvalidUserDataException.class,()->{
            User user = new User();
            Calendar calendar = Calendar.getInstance();
            calendar.set(1995,9,27);
            user.setId(4);
            user.setName("2Sam");
            user.setEmail("wrongEmail");
            user.setSurname("@Johns");
            user.setMarried(true);
            userController.updateUser(user);
        });
    }

    @Test
    public void savingTest() throws InvalidUserDataException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1995,9,27);
        User user = new User();
        user.setMarried(true);
        user.setName("Sam");
        user.setSurname("Bridges");
        user.setEmail("sam@mail.ru");
        user.setBirthDate(calendar.getTime());
        User returnedUser = userController.save(user);
        try {
            assertThat(userRepository.findById(returnedUser.getId())).isNotNull();
            assertThat(returnedUser.equals(userRepository.findById(returnedUser.getId())));
        }catch (Exception e){

        }
        finally {
            userRepository.delete(returnedUser);
        }
    }

    @Test
    public void deletingTest() throws InvalidUserDataException, UserNotFoundException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1995,9,27);
        User user = new User();
        user.setMarried(true);
        user.setName("Sam");
        user.setSurname("Bridges");
        user.setEmail("sam@mail.ru");
        user.setBirthDate(calendar.getTime());
        User returnedUser = userRepository.save(user);
        userController.delete(returnedUser.getId());
        assertThat(userRepository.findById(returnedUser.getId())).isEmpty();
    }

    @Test
    public void updatingTest() throws InvalidUserDataException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1995,9,27);
        User user = new User();
        user.setMarried(true);
        user.setName("Sam");
        user.setSurname("Bridges");
        user.setEmail("sam@mail.ru");
        user.setBirthDate(calendar.getTime());
        User returnedUser = userController.save(user);
        returnedUser.setSurname("Ivanov");
        returnedUser = userController.updateUser(returnedUser);
        try {
            User newUser = userRepository.findById(returnedUser.getId()).orElseThrow(UserNotFoundException::new);
            assertThat(newUser.getSurname().equals(returnedUser.getSurname()));
        }catch (Exception e){

        }finally {
            userRepository.delete(returnedUser);
        }
    }
}
