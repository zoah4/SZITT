package szitt.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import szitt.enums.RoleEnum;
import szitt.model.Attendant;
import szitt.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class AttendantRepositoryTest {

    @Autowired
    private AttendantRepository repository;

    @Test
    void shouldSaveAndFindAttendant() {
        User user = new User();
        user.setFirstname("Ivana");
        user.setLastname("Perić");
        user.setEmail("ivana.peric@example.com");
        user.setPassword("password");
        user.setRole(RoleEnum.POLAZNIK);

        Attendant attendant = new Attendant();
        attendant.setUser(user);

        Attendant saved = repository.save(attendant);
        Optional<Attendant> found = repository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getUser().getEmail()).isEqualTo("ivana.peric@example.com");
    }

    @Test
    void shouldReturnEmptyWhenNotFound() {
        Optional<Attendant> found = repository.findById(123L);
        assertThat(found).isEmpty();
    }
}
