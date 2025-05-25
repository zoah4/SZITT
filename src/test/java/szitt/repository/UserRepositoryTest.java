package szitt.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import szitt.enums.RoleEnum;
import szitt.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUser() {
        User user = new User();
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setRole(RoleEnum.POLAZNIK);

        User saved = userRepository.save(user);
        User found = userRepository.findById(saved.getId()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(found.getRole()).isEqualTo(RoleEnum.POLAZNIK);
    }

    @Test
    void shouldFindUserByEmail() {
        User user = new User();
        user.setFirstname("Jane");
        user.setLastname("Doe");
        user.setEmail("jane.doe@example.com");
        user.setPassword("password");
        user.setRole(RoleEnum.INSTRUKTOR);
        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("jane.doe@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getFirstname()).isEqualTo("Jane");
        assertThat(found.get().getRole()).isEqualTo(RoleEnum.INSTRUKTOR);
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        assertThat(userRepository.findById(999L)).isEmpty();
        assertThat(userRepository.findByEmail("nonexistent@example.com")).isEmpty();
    }
}