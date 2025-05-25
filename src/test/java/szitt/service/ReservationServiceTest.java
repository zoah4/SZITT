package szitt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import szitt.model.Reservation;
import szitt.repository.ReservationRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ReviewService reviewService;
    @Mock
    private NotificationService notificationService;
    @Mock
    private szitt.repository.AttendantRepository attendantRepository;
    @Mock
    private szitt.repository.InstructorRepository instructorRepository;
    @Mock
    private szitt.repository.SubjectRepository subjectRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReservationById() {
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        Optional<Reservation> result = reservationService.getReservation(1L);
        assert(result.isPresent());
    }
}
