package szitt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import szitt.dto.AttendantDTO;
import szitt.dto.RegisterDTO;
import szitt.model.Attendant;
import szitt.model.User;
import szitt.repository.AttendantRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class AttendantServiceTest {

    @Mock
    private AttendantRepository repository;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private AttendantService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEditProfile() {
        Attendant attendant = new Attendant();
        User user = new User();
        attendant.setUser(user);

        when(repository.findById(1L)).thenReturn(Optional.of(attendant));

        RegisterDTO userDto = new RegisterDTO("Ime", "Prezime", "email", "pass", null);
        AttendantDTO dto = new AttendantDTO(userDto, 0);

        service.editProfile(1L, dto);

        verify(repository).save(any(Attendant.class));
    }
}
