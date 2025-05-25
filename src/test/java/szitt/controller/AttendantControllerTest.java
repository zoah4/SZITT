package szitt.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import szitt.service.AttendantService;
import szitt.service.ReservationService;
import szitt.service.ReviewService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AttendantController.class)
public class AttendantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttendantService attendantService;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private ReviewService reviewService;

    @Test
    void shouldReturnAllAttendants() throws Exception {
        mockMvc.perform(get("/api/attendant/all"))
                .andExpect(status().isOk());
    }
}