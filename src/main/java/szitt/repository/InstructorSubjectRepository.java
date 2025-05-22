package szitt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import szitt.model.InstructorSubject;
import szitt.model.InstructorSubjectId;

@Repository
public interface InstructorSubjectRepository extends JpaRepository<InstructorSubject, InstructorSubjectId>{
    Iterable<InstructorSubject> findAllByInstructorId(Long instructorId);
    Iterable<InstructorSubject> findAllBySubjectId(Long subjectId);
    Iterable<InstructorSubject> findAllByInstructorIdAndSubjectId(Long instructorId, Long subjectId);

}
