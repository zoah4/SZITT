package szitt.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import szitt.enums.RoleEnum;
import szitt.model.Instructor;
import szitt.model.InstructorSubject;
import szitt.model.InstructorSubjectId;
import szitt.model.Subject;
import szitt.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class InstructorSubjectRepositoryTest {

    @Autowired
    private InstructorSubjectRepository repository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    void shouldSaveAndFindInstructorSubject() {
        Instructor instructor = createTestInstructor("instruktor@example.com");

        Subject subject = createTestSubject("Matematika");

        InstructorSubjectId id = new InstructorSubjectId(instructor.getId(), subject.getId());
        InstructorSubject instructorSubject = new InstructorSubject(instructor, subject);

        InstructorSubject saved = repository.save(instructorSubject);

        InstructorSubject found = repository.findById(id).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getInstructor().getId()).isEqualTo(instructor.getId());
        assertThat(found.getSubject().getId()).isEqualTo(subject.getId());
    }

    @Test
    void shouldFindAllByInstructorId() {
        Instructor instructor1 = createTestInstructor("instruktor1@example.com");
        Instructor instructor2 = createTestInstructor("instruktor2@example.com");
        Subject subject1 = createTestSubject("Matematika");
        Subject subject2 = createTestSubject("Fizika");

        repository.save(new InstructorSubject(instructor1, subject1));
        repository.save(new InstructorSubject(instructor1, subject2));
        repository.save(new InstructorSubject(instructor2, subject1));

        Iterable<InstructorSubject> results = repository.findAllByInstructorId(instructor1.getId());

        assertThat(results).hasSize(2);
        assertThat(results).allMatch(is -> is.getInstructor().getId().equals(instructor1.getId()));
    }

    @Test
    void shouldFindAllBySubjectId() {
        Instructor instructor1 = createTestInstructor("instruktor1@example.com");
        Instructor instructor2 = createTestInstructor("instruktor2@example.com");
        Subject subject = createTestSubject("Matematika");

        repository.save(new InstructorSubject(instructor1, subject));
        repository.save(new InstructorSubject(instructor2, subject));

        Iterable<InstructorSubject> results = repository.findAllBySubjectId(subject.getId());

        assertThat(results).hasSize(2);
        assertThat(results).allMatch(is -> is.getSubject().getId().equals(subject.getId()));
    }

    @Test
    void shouldFindAllByInstructorIdAndSubjectId() {
        Instructor instructor = createTestInstructor("instruktor@example.com");
        Subject subject1 = createTestSubject("Matematika");
        Subject subject2 = createTestSubject("Fizika");

        repository.save(new InstructorSubject(instructor, subject1));
        repository.save(new InstructorSubject(instructor, subject2));

        Iterable<InstructorSubject> results = repository.findAllByInstructorIdAndSubjectId(
                instructor.getId(), subject1.getId());

        assertThat(results).hasSize(1);
        assertThat(results.iterator().next().getSubject().getId()).isEqualTo(subject1.getId());
    }

    private Instructor createTestInstructor(String email) {
        User user = new User();
        user.setFirstname("Test");
        user.setLastname("Instruktor");
        user.setEmail(email);
        user.setPassword("password");
        user.setRole(RoleEnum.INSTRUKTOR);

        Instructor instructor = new Instructor();
        instructor.setUser(user);
        return instructorRepository.save(instructor);
    }

    private Subject createTestSubject(String name) {
        Subject subject = new Subject();
        subject.setName(name);
        return subjectRepository.save(subject);
    }
}