package szitt.controller;

import org.springframework.web.bind.annotation.*;
import szitt.dto.AttendantDTO;
import szitt.dto.ReservationDTO;
import szitt.dto.ReviewDTO;
import szitt.model.Reservation;
import szitt.service.AttendantService;
import szitt.model.Attendant;
import szitt.service.ReservationService;
import szitt.service.ReviewService;

import java.util.Optional;

@RestController
@RequestMapping("/api/attendant")
public class AttendantController {
    private final AttendantService attendantService;
    private final ReservationService reservationService;
    private final ReviewService reviewService;
    public AttendantController(AttendantService attendantService, ReservationService reservationservice, ReviewService reviewService) {
        this.attendantService = attendantService;
        this.reservationService = reservationservice;
        this.reviewService = reviewService;
    }

    @GetMapping("/all")
    public Iterable<Attendant> getAllAttendants() {
        return this.attendantService.getAllAttendants();
    }

    @GetMapping("/{id}")
    public Optional<Attendant> getAttendant(@PathVariable Long id) {
        return this.attendantService.getAttendant(id);
    }

    @PutMapping("{reservationId}/user/{userId}/accept")
    public void acceptReservation(@PathVariable Long reservationId, @PathVariable Long userId) {
        this.reservationService.confirm(reservationId, userId);
    }

    @PutMapping("{reservationId}/user/{userId}/cancel")
    public void cancelReservation(@PathVariable Long reservationId, @PathVariable Long userId) {
        this.reservationService.cancel(reservationId, userId);
    }

    @PutMapping("/{id}/edit")
    public void editProfile(@PathVariable Long id, @RequestBody AttendantDTO dto) {
        this.attendantService.editProfile(id, dto);
    }

    @PostMapping("/{attendantId}/instructor/{instructorId}/reservation")
    public void createReservation(@PathVariable Long attendantId, @PathVariable Long instructorId, @RequestBody ReservationDTO dto) {
        this.reservationService.createReservation(attendantId, instructorId, dto);
    }

    @PostMapping("/{attendantId}/reservation/{reservationId}/reviews")
    public void writeReview(@PathVariable Long attendantId, @PathVariable Long reservationId, @RequestBody ReviewDTO dto) {
        this.reviewService.writeReview(attendantId, reservationId, dto);
    }

    @GetMapping("/{id}/reservations")
    public Iterable<Reservation> getAcceptedReservations(@PathVariable Long id) {
        return this.reservationService.getAcceptedReservations(id);
    }
}
