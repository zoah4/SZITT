package szitt.controller;

import org.springframework.web.bind.annotation.*;
import szitt.model.ReservationReview;
import szitt.service.ReservationService;
import szitt.model.Reservation;

import java.util.Optional;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService service;
    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Iterable<Reservation> getAllReservations() {
        return this.service.getAllReservations();
    }

    @GetMapping("/{id}")
    public Optional<Reservation> getReservation(@PathVariable Long id) {
        return this.service.getReservation(id);
    }

    @PutMapping("/{reservationId}/user/{userId}/confirm")
    public void confirm(@PathVariable Long reservationId, @PathVariable Long userId) {
        this.service.confirm(reservationId, userId);
    }

    @PutMapping("/{reservationId}/user/{userId}/reject")
    public void reject(@PathVariable Long reservationId, @PathVariable Long userId) {
        this.service.reject(reservationId, userId);
    }

    @PutMapping("/{reservationId}/user/{userId}/cancel")
    public void cancel(@PathVariable Long reservationId, @PathVariable Long userId) {
        this.service.cancel(reservationId, userId);
    }

    @PutMapping("/{reservationId}/user/{userId}/complete")
    public void markAsCompleted(@PathVariable Long reservationId, @PathVariable Long userId) {
        this.service.markAsCompleted(reservationId, userId);
    }

    @PutMapping("/{reservationId}/user/{userId}/wait")
    public void onWait(@PathVariable Long reservationId, @PathVariable Long userId) {
        this.service.onWait(reservationId, userId);
    }

    @GetMapping("/{reservationId}/review")
    public Optional<ReservationReview> getReservationWithReview(@PathVariable Long reservationId) {
        return this.service.getReservationWithReview(reservationId);
    }

}
