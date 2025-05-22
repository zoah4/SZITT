package szitt.service;

import org.springframework.stereotype.Service;
import szitt.model.Instructor;
import szitt.model.InstructorSubjectId;
import szitt.model.Subject;
import szitt.repository.InstructorRepository;
import szitt.repository.InstructorSubjectRepository;
import szitt.model.InstructorSubject;
import szitt.repository.SubjectRepository;

@Service
public class InstructorSubjectService {
    private final InstructorRepository instructorRepository;
    private final SubjectRepository subjectRepository;
    private final InstructorSubjectRepository instructorSubjectRepository;
    public InstructorSubjectService(InstructorRepository instructorRepository, SubjectRepository subjectRepository, InstructorSubjectRepository instructorSubjectRepository) {
        this.instructorRepository = instructorRepository;
        this.subjectRepository = subjectRepository;
        this.instructorSubjectRepository = instructorSubjectRepository;
    }

    public Iterable<InstructorSubject> getAllInstructorSubjects() {
        return this.instructorSubjectRepository.findAll();
    }

    public Iterable<InstructorSubject> getInstructorsSubject(Long instructorId) {
        return this.instructorSubjectRepository.findAllByInstructorId(instructorId);
    }

    public Iterable<InstructorSubject> getSubjectsInstructor(Long subjectId) {
        return this.instructorSubjectRepository.findAllBySubjectId(subjectId);
    }

    public Iterable<InstructorSubject> getInstructorAndSubject(Long instructorId, Long subjectId) {
        return this.instructorSubjectRepository.findAllByInstructorIdAndSubjectId(instructorId, subjectId);
    }

    public void assignSubjectToInstructor(Long instructorId, Long subjectId) {
        Instructor instructor = instructorRepository.findById(instructorId).get();
        Subject subject = subjectRepository.findById(subjectId).get();

        InstructorSubject link = new InstructorSubject(instructor, subject);
        instructorSubjectRepository.save(link);
    }

    public void removeSubjectFromInstructor(Long instructorId, Long subjectId) {
        InstructorSubjectId id = new InstructorSubjectId(instructorId, subjectId);
        instructorSubjectRepository.deleteById(id);
    }
}
