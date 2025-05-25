package szitt.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import szitt.dto.RegisterDTO;
import szitt.enums.RoleEnum;
import szitt.model.User;
import szitt.repository.UserRepository;
import szitt.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(UserService.class)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndRetrieveUser() {
        User user = new User();
        user.setFirstname("Integration");
        user.setLastname("Test");
        user.setEmail("integration@test.com");
        user.setPassword("password");
        user.setRole(RoleEnum.POLAZNIK);

        userRepository.save(user);

        Optional<User> savedUser = userRepository.findByEmail("integration@test.com");
        
        Optional<User> foundUser = userRepository.findById(savedUser.get().getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("integration@test.com");
    }

    @Test
    void shouldDeleteUser() {
        User user = new User();
        user.setFirstname("ToDelete");
        user.setEmail("delete@test.com");
        user.setPassword("password");
        user.setRole(RoleEnum.POLAZNIK);
        User savedUser = userRepository.save(user);

        userService.deleteUser(savedUser.getId());

        assertThat(userRepository.findById(savedUser.getId())).isEmpty();
    }

    @Test
    void shouldFindUserById() {
        User user = new User();
        user.setFirstname("ToFind");
        user.setEmail("find@test.com");
        user.setPassword("password");
        user.setRole(RoleEnum.INSTRUKTOR);
        User savedUser = userRepository.save(user);

        Optional<User> foundUser = userService.getUser(savedUser.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("find@test.com");
    }
}