package szitt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import szitt.enums.StatusEnum;
import szitt.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    Iterable<Reservation> findByInstructorIdAndStatus(Long userId, StatusEnum statusEnum);
    Iterable<Reservation> findByAttendantIdAndStatus(Long userId, StatusEnum statusEnum);


    @Query("SELECT r FROM Reservation r WHERE " +
            "(:instructorId IS NULL OR r.instructor.id = :instructorId) AND " +
            "(:attendantId IS NULL OR r.attendant.id = :attendantId) AND " +
            "(:subjectId IS NULL OR r.subject.id = :subjectId)")
    Iterable<Reservation> findByCriteria(@Param("instructorId") Long instructorId,
                                         @Param("attendantId") Long attendantId,
                                         @Param("subjectId") Long subjectId);
}
