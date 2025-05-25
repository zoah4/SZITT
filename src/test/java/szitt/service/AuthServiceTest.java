package szitt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import szitt.dto.LoginDTO;
import szitt.dto.RegisterDTO;
import szitt.enums.RoleEnum;
import szitt.model.Attendant;
import szitt.model.Instructor;
import szitt.model.User;
import szitt.repository.AttendantRepository;
import szitt.repository.InstructorRepository;
import szitt.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private AttendantRepository attendantRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterAsAttendant() {
        RegisterDTO dto = new RegisterDTO("Ana", "Anić", "ana@example.com", "pass123", RoleEnum.POLAZNIK);
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encoded");

        authService.register(dto);

        verify(userRepository).save(any(User.class));
        verify(attendantRepository).save(any(Attendant.class));
    }

    @Test
    void testLoginSuccess() {
        LoginDTO dto = new LoginDTO("ana@example.com", "pass123");
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword("encoded");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(dto.getPassword(), "encoded")).thenReturn(true);

        User loggedUser = authService.login(dto);

        assertEquals(dto.getEmail(), loggedUser.getEmail());
    }

    @Test
    void testLoginFailsOnWrongPassword() {
        LoginDTO dto = new LoginDTO("ana@example.com", "wrongpass");
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword("encoded");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(dto.getPassword(), "encoded")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authService.login(dto));
    }
}
