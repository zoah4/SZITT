package szitt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import szitt.model.Review;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
    Iterable<Review> findAllByInstructorId(Long instructorId);
    Iterable<Review> findAllByAttendantId(Long attendantId);
    Optional<Review> findByReservationId(Long reviewId);

}
