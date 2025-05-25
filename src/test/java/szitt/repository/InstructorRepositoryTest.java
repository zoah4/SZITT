package szitt.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import szitt.enums.RoleEnum;
import szitt.model.Instructor;
import szitt.model.User;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class InstructorRepositoryTest {

    @Autowired
    private InstructorRepository repository;

    @Test
    void shouldSaveAndFindInstructor() {
        User user = new User();
        user.setFirstname("Marko");
        user.setLastname("Marković");
        user.setEmail("marko.markovic@example.com");
        user.setPassword("password");
        user.setRole(RoleEnum.INSTRUKTOR);

        Instructor instructor = new Instructor();
        instructor.setUser(user);
        instructor.setDescription("Profesionalni instruktor");
        instructor.setPricePerHour(BigDecimal.valueOf(50));

        // Čuvanje i pronalaženje instruktora
        Instructor saved = repository.save(instructor);
        Optional<Instructor> found = repository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getUser().getEmail()).isEqualTo("marko.markovic@example.com");
        assertThat(found.get().getDescription()).isEqualTo("Profesionalni instruktor");
        assertThat(found.get().getPricePerHour()).isEqualTo(BigDecimal.valueOf(50));
    }

    @Test
    void shouldReturnEmptyWhenNotFound() {
        Optional<Instructor> found = repository.findById(999L);
        assertThat(found).isEmpty();
    }
}