package szitt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import szitt.model.User;
import szitt.repository.UserRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteUser() {
        service.deleteUser(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void testFindUserById() {
        User user = new User();
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> result = service.getUser(1L);
        assert(result.isPresent());
    }
}