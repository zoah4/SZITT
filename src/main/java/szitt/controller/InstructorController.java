package szitt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import szitt.dto.InstructorDTO;
import szitt.model.Reservation;
import szitt.model.Review;
import szitt.service.InstructorService;
import szitt.model.Instructor;
import szitt.service.ReservationService;
import szitt.service.ReviewService;

import java.util.Optional;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {
    private final InstructorService instructorService;
    private final ReviewService reviewService;
    private final ReservationService reservationService;
    public InstructorController(InstructorService instructorService, ReviewService reviewService, ReservationService reservationservice) {
        this.instructorService = instructorService;
        this.reviewService = reviewService;
        this.reservationService = reservationservice;
    }

    @GetMapping("/all")
    public Iterable<Instructor> getAllInstructors() {
        return this.instructorService.getAllInstructors();
    }

    @GetMapping("/{id}")
    public Optional<Instructor> getInstructor(@PathVariable Long id) {
        return this.instructorService.getInstructor(id);
    }

    @GetMapping("/reviews/{instructorId}")
    public Iterable<Review> findReviews(@PathVariable Long instructorId) {
        return this.reviewService.getAllByInstructor(instructorId);
    }

    @PutMapping("{reservationId}/user/{userId}/accept")
    public void acceptReservation(@PathVariable Long reservationId, @PathVariable Long userId) {
        this.reservationService.confirm(reservationId, userId);
    }

    @PutMapping("{reservationId}/user/{userId}/reject")
    public void rejectReservation(@PathVariable Long reservationId, @PathVariable Long userId) {
        this.reservationService.reject(reservationId, userId);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<String> editProfile(@PathVariable Long id, @RequestBody InstructorDTO dto) {
        this.instructorService.editProfile(id, dto);
        return ResponseEntity.ok("Profil je uspješno ažuriran.");
    }

    @GetMapping("/{id}/reservations")
    public Iterable<Reservation> getPendingReservations(@PathVariable Long id) {
        return this.reservationService.getPendingReservations(id);
    }

    @GetMapping("/{id}/reservations/accepted")
    public Iterable<Reservation> getAcceptedReservations(@PathVariable Long id) {
        return this.reservationService.getAcceptedReservations(id);
    }

    @GetMapping("/{id}/reservations/completed")
    public Iterable<Reservation> getCompletedReservations(@PathVariable Long id) {
        return this.reservationService.getCompletedReservations(id);
    }

}
