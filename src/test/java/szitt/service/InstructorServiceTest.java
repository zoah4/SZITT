package szitt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import szitt.dto.InstructorDTO;
import szitt.dto.RegisterDTO;
import szitt.model.Instructor;
import szitt.model.User;
import szitt.repository.InstructorRepository;
import szitt.repository.ReviewRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class InstructorServiceTest {

    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private InstructorService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEditProfile() {
        Instructor instructor = new Instructor();
        User user = new User();
        instructor.setUser(user);

        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

        RegisterDTO registerDTO = new RegisterDTO("Ime", "Prezime", "email", "pass", null);
        InstructorDTO dto = new InstructorDTO(registerDTO, "Opis", BigDecimal.TEN);

        service.editProfile(1L, dto);

        verify(instructorRepository).save(any(Instructor.class));
    }
}