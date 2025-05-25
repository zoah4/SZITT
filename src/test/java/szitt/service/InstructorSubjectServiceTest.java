package szitt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import szitt.model.Instructor;
import szitt.model.Subject;
import szitt.repository.InstructorRepository;
import szitt.repository.SubjectRepository;
import szitt.repository.InstructorSubjectRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class InstructorSubjectServiceTest {

    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private InstructorSubjectRepository instructorSubjectRepository;
    @InjectMocks
    private InstructorSubjectService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAssignSubjectToInstructor() {
        Instructor instructor = new Instructor();
        Subject subject = new Subject();

        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(subjectRepository.findById(2L)).thenReturn(Optional.of(subject));

        service.assignSubjectToInstructor(1L, 2L);

        verify(instructorSubjectRepository).save(any());
    }
}
