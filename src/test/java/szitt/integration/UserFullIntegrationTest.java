package szitt.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import szitt.enums.RoleEnum;
import szitt.model.User;
import szitt.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserFullIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldCreateRetrieveAndDeleteUser() throws Exception {
        String userJson = """
        {
            "firstname": "FullTest",
            "lastname": "User",
            "email": "fulltest@example.com",
            "password": "password",
            "role": "POLAZNIK"
        }
        """;

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("fulltest@example.com"));

        User savedUser = userRepository.findByEmail("fulltest@example.com").orElseThrow();

        mockMvc.perform(get("/api/user/" + savedUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value("FullTest"));

        mockMvc.perform(get("/api/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));

        mockMvc.perform(delete("/api/user/" + savedUser.getId()))
                .andExpect(status().isNoContent());

        assertThat(userRepository.findByEmail("fulltest@example.com")).isEmpty();
    }

    @Test
    void shouldReturnNotFoundForNonExistingUser() throws Exception {
        mockMvc.perform(get("/api/user/999"))
                .andExpect(status().isNotFound());
    }
}
