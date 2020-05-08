package ru.sergeyrusakov.testingTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.sergeyrusakov.testingTask.controllers.EmployeeController;
import ru.sergeyrusakov.testingTask.entities.Employee;
import ru.sergeyrusakov.testingTask.repositories.EmployeeRepository;
import ru.sergeyrusakov.testingTask.repositories.OrganizationRepository;
import ru.sergeyrusakov.testingTask.repositories.PositionRepository;

import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerValidationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    public ControllerValidationTest(){
        employee = new Employee();
        employee.setName("Sam");
        employee.setSurname("Bridges");
        employee.setEmail("sambridges@mail.ru");
        employee.setPhoneNumber("89824428864");
        employee.setTimeAdded(LocalDateTime.now());
        employee.setTimeUpdated(employee.getTimeAdded());
        employee.setWorking(true);
        employee.setBirthDate(LocalDate.parse("1995-10-27"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldNotAddEmployee() throws Exception {
        employee.setName("Sam1");
        employee.setOrganization(organizationRepository.findById(1).get());
        employee.setPosition(positionRepository.findById(1).get());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        employee.setName("Sam");
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
