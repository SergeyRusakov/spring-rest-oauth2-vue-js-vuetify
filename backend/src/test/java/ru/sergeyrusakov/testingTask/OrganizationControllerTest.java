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
import ru.sergeyrusakov.testingTask.entities.Organization;
import ru.sergeyrusakov.testingTask.repositories.OrganizationRepository;

import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class OrganizationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrganizationRepository organizationRepository;
    private Organization organization;

    public OrganizationControllerTest(){
        organization = new Organization();
        organization.setName("Toyota");
        organization.setEmail("toyota@toyota.com");
        organization.setCountry("Japan");
        organization.setCity("Tokyo");
        organization.setStreet("Main");
        organization.setTimeAdded(LocalDateTime.now());
        organization.setTimeUpdated(organization.getTimeAdded());
        organization.setPhoneNumber("89824428864");
    }

    @Test
    @WithMockUser
    public void shouldReturnData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organizations/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser
    public void shouldReturnOrganization() throws Exception {
        organization = organizationRepository.save(organization);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organizations/"+organization.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(organization.getName()));
        organizationRepository.deleteById(organization.getId());
    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldDeleteOrganization() throws Exception {
        organization = organizationRepository.save(organization);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/organizations/"+organization.getId())
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertThat(organizationRepository.findById(organization.getId())).isEmpty();
    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldAddOrganization() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(organization))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo((x) -> {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.registerModule(new JavaTimeModule());
                        Reader reader = new StringReader(x.getResponse().getContentAsString());
                        organization = mapper.readValue(reader, Organization.class);
                    });
        Assertions.assertThat(organizationRepository
                .findById(organization.getId()).get().getName().equals(organization.getName()));
        organizationRepository.deleteById(organization.getId());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldUpdateOrganization() throws Exception {
        organization = organizationRepository.save(organization);
        String newName = "Mitsubishi";
        organization.setName(newName);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(organization))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertThat(organizationRepository.findById(organization.getId()).get().getName().equals(newName));
        organizationRepository.deleteById(organization.getId());
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
