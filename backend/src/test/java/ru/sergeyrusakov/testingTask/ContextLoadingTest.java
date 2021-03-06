package ru.sergeyrusakov.testingTask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sergeyrusakov.testingTask.controllers.AppController;
import ru.sergeyrusakov.testingTask.controllers.UserController;
import ru.sergeyrusakov.testingTask.repositories.GitHubUserRepository;
import ru.sergeyrusakov.testingTask.repositories.UserRepository;
import ru.sergeyrusakov.testingTask.service.AppControllerService;
import ru.sergeyrusakov.testingTask.service.UserControllerService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ContextLoadingTest {
    @Autowired
    private UserController userController;

    @Autowired
    private AppController appController;

    @Autowired
    private GitHubUserRepository gitHubUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppControllerService appControllerService;

    @Autowired
    private UserControllerService userControllerService;

    @Test
    public void userControllerTest(){
        assertThat(userController).isNotNull();
    }

    @Test
    public void appControllerTest(){
        assertThat(appController).isNotNull();
    }

    @Test
    public void gitHubUserRepositoryTest(){
        assertThat(gitHubUserRepository).isNotNull();
    }

    @Test
    public void userRepositoryTest(){
        assertThat(userRepository).isNotNull();
    }

    @Test
    public void appControllerServiceTest(){
        assertThat(appControllerService).isNotNull();
    }

    @Test
    public void userControllerServiceTest(){
        assertThat(userControllerService).isNotNull();
    }
}
