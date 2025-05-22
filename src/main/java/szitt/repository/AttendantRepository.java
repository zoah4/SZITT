package szitt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import szitt.model.Attendant;

@Repository
public interface AttendantRepository extends JpaRepository<Attendant, Long> {
}
