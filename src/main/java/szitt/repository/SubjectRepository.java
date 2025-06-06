package szitt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import szitt.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
