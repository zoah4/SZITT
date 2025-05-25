package szitt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import szitt.dto.NotificationDTO;
import szitt.dto.RegisterDTO;
import szitt.enums.TypeEnum;
import szitt.model.User;
import szitt.repository.NotificationRepository;
import szitt.repository.UserRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private NotificationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendNotification() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        RegisterDTO userDTO = new RegisterDTO();
        userDTO.setEmail("test@example.com");

        NotificationDTO dto = new NotificationDTO();
        dto.setUser(userDTO);
        dto.setContent("Sadržaj");
        dto.setType(TypeEnum.OPCENITO);

        service.sendNotification(dto);

        verify(notificationRepository).save(any());
    }
}