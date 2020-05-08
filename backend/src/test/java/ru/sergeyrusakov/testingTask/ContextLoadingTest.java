package ru.sergeyrusakov.testingTask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sergeyrusakov.testingTask.controllers.AppController;
import ru.sergeyrusakov.testingTask.controllers.EmployeeController;
import ru.sergeyrusakov.testingTask.controllers.OrganizationController;
import ru.sergeyrusakov.testingTask.controllers.PositionController;
import ru.sergeyrusakov.testingTask.repositories.GitHubUserRepository;
import ru.sergeyrusakov.testingTask.repositories.EmployeeRepository;
import ru.sergeyrusakov.testingTask.repositories.OrganizationRepository;
import ru.sergeyrusakov.testingTask.repositories.PositionRepository;
import ru.sergeyrusakov.testingTask.service.AppControllerService;
import ru.sergeyrusakov.testingTask.service.EmployeeControllerService;
import ru.sergeyrusakov.testingTask.service.OrganizationControllerService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ContextLoadingTest {
    @Autowired
    private EmployeeController employeeController;
    @Autowired
    private OrganizationController organizationController;
    @Autowired
    private PositionController positionController;
    @Autowired
    private AppController appController;
    @Autowired
    private GitHubUserRepository gitHubUserRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private AppControllerService appControllerService;
    @Autowired
    private EmployeeControllerService employeeControllerService;
    @Autowired
    private OrganizationControllerService organizationControllerService;

    @Test
    public void employeeControllerTest(){
        assertThat(employeeController).isNotNull();
    }
    @Test
    public void organizationControllerTest(){
        assertThat(organizationController).isNotNull();
    }
    @Test
    public void positionControllerTest(){
        assertThat(positionController).isNotNull();
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
    public void organizationRepositoryTest(){
        assertThat(organizationRepository).isNotNull();
    }
    @Test
    public void employeeRepositoryTest(){
        assertThat(employeeRepository).isNotNull();
    }
    @Test
    public void positionRepositoryTest(){
        assertThat(positionRepository).isNotNull();
    }
    @Test
    public void appControllerServiceTest(){
        assertThat(appControllerService).isNotNull();
    }
    @Test
    public void userControllerServiceTest(){
        assertThat(employeeControllerService).isNotNull();
    }
    @Test
    public void organizationControllerServiceTest(){
        assertThat(organizationControllerService).isNotNull();
    }

}
