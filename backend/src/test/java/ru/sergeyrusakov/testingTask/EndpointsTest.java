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
import ru.sergeyrusakov.testingTask.entities.User;
import ru.sergeyrusakov.testingTask.exceptions.UserNotFoundException;
import ru.sergeyrusakov.testingTask.repositories.UserRepository;

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
    private UserRepository userRepository;

    private User user;

    public EndpointsTest() {
        user = new User();
        user.setMarried(true);
        user.setName("Sam");
        user.setSurname("Bridges");
        user.setEmail("sam@mail.ru");
        user.setBirthDate(LocalDate.parse("1995-09-27"));
        user.setCreationDate(LocalDateTime.now());
        user.setTimeLastEdited(LocalDateTime.now());
    }

    @Test
    public void shouldRedirectWhileNotAuthenticated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
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
    public void shouldReturnUser() throws Exception {
        try {
            user = userRepository.save(user);
            mockMvc.perform(MockMvcRequestBuilders.get("/users/" + user.getId()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(user.getName()));
        }catch (Exception e){

        }
        finally {
            userRepository.delete(user);
        }
    }

    @Test
    @WithMockUser(roles = {"ADMIN"} )
    public void shouldDeleteData() throws Exception {
        user = userRepository.save(user);
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+String.valueOf(user.getId()))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        try {
            Assertions.assertThat(userRepository.findById(user.getId())).isEmpty();
        }catch (Exception e){
            userRepository.delete(user);
        }
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldAddUser() throws Exception{
        user = new User();
        user.setMarried(true);
        user.setName("Sam");
        user.setSurname("Bridges");
        user.setEmail("sam@mail.ru");
        user.setBirthDate(LocalDate.parse("1995-09-27"));
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo((x) -> {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.registerModule(new JavaTimeModule());
                        Reader reader = new StringReader(x.getResponse().getContentAsString());
                        user = mapper.readValue(reader, User.class);
                    });
            try {
                Assertions.assertThat(userRepository.findById(user.getId())).isNotEmpty();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                userRepository.delete(user);
            }
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldNotAddUser() throws Exception{
        user = new User();
        user.setMarried(true);
        user.setName("Sam1");
        user.setSurname("Bridges");
        user.setEmail("sam@mail.ru");
        user.setBirthDate(LocalDate.parse("1995-09-27"));
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldUpdateUser() throws Exception{
        user = new User();
        user.setMarried(true);
        user.setName("Sam");
        user.setSurname("Bridges");
        user.setEmail("sam@mail.ru");
        user.setBirthDate(LocalDate.parse("1995-09-27"));
        user.setTimeLastEdited(LocalDateTime.now());
        user.setCreationDate(LocalDateTime.now());
        user = userRepository.save(user);
        String newName = "John";
        user.setName(newName);
        mockMvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        try {
            User newUser = userRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new);
            Assertions.assertThat(newUser.getName().equals(user.getName()));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            userRepository.delete(user);
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
