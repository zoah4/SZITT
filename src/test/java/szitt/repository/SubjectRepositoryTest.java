package szitt.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import szitt.model.Subject;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    void shouldSaveAndFindSubject() {
        Subject subject = new Subject();
        subject.setName("Mathematics");

        Subject saved = subjectRepository.save(subject);
        Subject found = subjectRepository.findById(saved.getId()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Mathematics");
    }

    @Test
    void shouldFindAllSubjects() {
        Subject subject1 = new Subject();
        subject1.setName("Mathematics");
        subjectRepository.save(subject1);

        Subject subject2 = new Subject();
        subject2.setName("Physics");
        subjectRepository.save(subject2);

        List<Subject> subjects = subjectRepository.findAll();

        assertThat(subjects).hasSize(2);
        assertThat(subjects).extracting(Subject::getName)
                .containsExactlyInAnyOrder("Mathematics", "Physics");
    }

    @Test
    void shouldReturnEmptyWhenSubjectNotFound() {
        assertThat(subjectRepository.findById(999L)).isEmpty();
    }
}