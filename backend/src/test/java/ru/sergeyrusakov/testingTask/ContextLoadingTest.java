package ru.sergeyrusakov.testingTask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sergeyrusakov.testingTask.controllers.AppController;
import ru.sergeyrusakov.testingTask.controllers.EmployeeController;
import ru.sergeyrusakov.testingTask.repositories.GitHubUserRepository;
import ru.sergeyrusakov.testingTask.repositories.EmployeeRepository;
import ru.sergeyrusakov.testingTask.service.AppControllerService;
import ru.sergeyrusakov.testingTask.service.EmployeeControllerService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ContextLoadingTest {
    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private AppController appController;

    @Autowired
    private GitHubUserRepository gitHubUserRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AppControllerService appControllerService;

    @Autowired
    private EmployeeControllerService employeeControllerService;

    @Test
    public void employeeControllerTest(){
        assertThat(employeeController).isNotNull();
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
    public void employeeRepositoryTest(){
        assertThat(employeeRepository).isNotNull();
    }

    @Test
    public void appControllerServiceTest(){
        assertThat(appControllerService).isNotNull();
    }

    @Test
    public void userControllerServiceTest(){
        assertThat(employeeControllerService).isNotNull();
    }
}
