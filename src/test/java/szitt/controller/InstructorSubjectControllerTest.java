package szitt.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import szitt.service.InstructorSubjectService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InstructorSubjectController.class)
public class InstructorSubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InstructorSubjectService service;

    @Test
    void shouldReturnAllInstructorSubjects() throws Exception {
        mockMvc.perform(get("/api/instructor-subject/all"))
                .andExpect(status().isOk());
    }
}
