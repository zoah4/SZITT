package szitt.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import szitt.service.InstructorService;
import szitt.service.ReservationService;
import szitt.service.ReviewService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InstructorController.class)
public class InstructorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InstructorService instructorService;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private ReservationService reservationService;

    @Test
    void shouldReturnAllInstructors() throws Exception {
        mockMvc.perform(get("/api/instructor/all"))
                .andExpect(status().isOk());
    }
}