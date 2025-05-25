package szitt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import szitt.model.Subject;
import szitt.repository.SubjectRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class SubjectServiceTest {

    @Mock
    private SubjectRepository repository;
    @InjectMocks
    private SubjectService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSubject() {
        service.createSubject("Matematika");
        verify(repository).save(any(Subject.class));
    }

    @Test
    void testDeleteSubject() {
        service.deleteSubject(1L);
        verify(repository).deleteById(1L);
    }
}