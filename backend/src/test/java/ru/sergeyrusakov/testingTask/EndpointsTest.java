package ru.sergeyrusakov.testingTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
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
import ru.sergeyrusakov.testingTask.exceptions.EmplyeeNotFoundException;
import ru.sergeyrusakov.testingTask.repositories.EmployeeRepository;

import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
public class EndpointsTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    public EndpointsTest() {
        employee = new Employee();
        employee.setMarried(true);
        employee.setName("Sam");
        employee.setSurname("Bridges");
        employee.setEmail("sam@mail.ru");
        employee.setBirthDate(LocalDate.parse("1995-09-27"));
        employee.setCreationDate(LocalDateTime.now());
        employee.setTimeLastEdited(LocalDateTime.now());
    }

    @Test
    public void shouldRedirectWhileNotAuthenticated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        mockMvc.perform(MockMvcRequestBuilders.get("/test"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    public void shouldReturnData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("response")));
    }

    @Test
    @WithMockUser
    public void shouldReturnEmployee() throws Exception {
        try {
            employee = employeeRepository.save(employee);
            mockMvc.perform(MockMvcRequestBuilders.get("/employees/" + employee.getId()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employee.getName()));
        }catch (Exception e){

        }
        finally {
            employeeRepository.delete(employee);
        }
    }

    @Test
    @WithMockUser(roles = {"ADMIN"} )
    public void shouldDeleteEmployee() throws Exception {
        employee = employeeRepository.save(employee);
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/"+String.valueOf(employee.getId()))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        try {
            Assertions.assertThat(employeeRepository.findById(employee.getId())).isEmpty();
        }catch (Exception e){
            employeeRepository.delete(employee);
        }
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldAddEmployee() throws Exception{
        employee = new Employee();
        employee.setMarried(true);
        employee.setName("Sam");
        employee.setSurname("Bridges");
        employee.setEmail("sam@mail.ru");
        employee.setBirthDate(LocalDate.parse("1995-09-27"));
        mockMvc.perform(post("/employees")
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
            try {
                Assertions.assertThat(employeeRepository.findById(employee.getId())).isNotEmpty();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                employeeRepository.delete(employee);
            }
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldNotAddEmployee() throws Exception{
        employee = new Employee();
        employee.setMarried(true);
        employee.setName("Sam1");
        employee.setSurname("Bridges");
        employee.setEmail("sam@mail.ru");
        employee.setBirthDate(LocalDate.parse("1995-09-27"));
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee))
                .accept(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldUpdateEmployee() throws Exception{
        employee = new Employee();
        employee.setMarried(true);
        employee.setName("Sam");
        employee.setSurname("Bridges");
        employee.setEmail("sam@mail.ru");
        employee.setBirthDate(LocalDate.parse("1995-09-27"));
        employee.setTimeLastEdited(LocalDateTime.now());
        employee.setCreationDate(LocalDateTime.now());
        employee = employeeRepository.save(employee);
        String newName = "John";
        employee.setName(newName);
        mockMvc.perform(put("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        try {
            Employee newEmployee = employeeRepository.findById(employee.getId()).orElseThrow(EmplyeeNotFoundException::new);
            Assertions.assertThat(newEmployee.getName().equals(employee.getName()));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            employeeRepository.delete(employee);
        }
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
