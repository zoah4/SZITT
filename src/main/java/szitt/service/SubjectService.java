package szitt.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import szitt.repository.SubjectRepository;
import szitt.model.Subject;

import java.util.Optional;

@Service
public class SubjectService {
    private final SubjectRepository repository;
    public SubjectService(SubjectRepository repository) {
        this.repository = repository;
    }

    public Iterable<Subject> getAllSubjects() {
        return this.repository.findAll((Sort.by("id")));
    }

    public Optional<Subject> getSubject(Long id) {
        return this.repository.findById(id);
    }

    public void createSubject(String name) {
        Subject subject = new Subject(name);
        this.repository.save(subject);
    }

    public void updateSubject(Long id, String name) {
        Subject subject = this.repository.findById(id).get();
        subject.setName(name);
        this.repository.save(subject);
    }

    public void deleteSubject(Long id) {
        this.repository.deleteById(id);
    }
}
