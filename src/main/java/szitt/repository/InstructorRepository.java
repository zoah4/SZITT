package szitt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import szitt.model.Instructor;
import szitt.model.Review;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long>{
}
