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
import ru.sergeyrusakov.testingTask.entities.Employee;
import ru.sergeyrusakov.testingTask.entities.Organization;
import ru.sergeyrusakov.testingTask.repositories.EmployeeRepository;
import ru.sergeyrusakov.testingTask.repositories.OrganizationRepository;
import ru.sergeyrusakov.testingTask.repositories.PositionRepository;

import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private MockMvc mockMvc;

    private Employee employee;

    public EmployeeControllerTest(){
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
    @WithMockUser
    public void shouldReturnData() throws Exception {
        employee.setOrganization(organizationRepository.findById(1).get());
        employee.setPosition(positionRepository.findById(1).get());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser
    public void shouldReturnEmployee() throws Exception {
        employee.setOrganization(organizationRepository.findById(1).get());
        employee.setPosition(positionRepository.findById(1).get());
        employee = employeeRepository.save(employee);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/"+employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employee.getName()));
        employeeRepository.deleteById(employee.getId());
    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldDeleteEmployee() throws Exception {
        employee.setOrganization(organizationRepository.findById(1).get());
        employee.setPosition(positionRepository.findById(1).get());
        employee = employeeRepository.save(employee);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/"+employee.getId())
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertThat(employeeRepository.findById(employee.getId())).isEmpty();
    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldAddEmployee() throws Exception {
        employee.setOrganization(organizationRepository.findById(1).get());
        employee.setPosition(positionRepository.findById(1).get());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo((x) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.registerModule(new JavaTimeModule());
                    Reader reader = new StringReader(x.getResponse().getContentAsString());
                    employee = mapper.readValue(reader, Employee.class);
                });
        Assertions.assertThat(employeeRepository
                .findById(employee.getId()).get().getName().equals(employee.getName()));
        employeeRepository.deleteById(employee.getId());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldUpdateEmployee() throws Exception {
        employee.setOrganization(organizationRepository.findById(1).get());
        employee.setPosition(positionRepository.findById(1).get());
        employee = employeeRepository.save(employee);
        String newName = "John";
        employee.setName(newName);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertThat(employeeRepository.findById(employee.getId()).get().getName().equals(newName));
        employeeRepository.deleteById(employee.getId());
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
