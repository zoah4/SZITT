package szitt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import szitt.enums.StatusEnum;
import szitt.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    Iterable<Reservation> findByInstructorIdAndStatus(Long instructorId, StatusEnum status);
    Iterable<Reservation> findByAttendantIdAndStatus(Long attendantId, StatusEnum status);
}
