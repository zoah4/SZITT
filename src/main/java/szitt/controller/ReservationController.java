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

    @PutMapping("/{id}/confirm")
    public void confirm(@PathVariable Long id) {
        this.service.confirm(id);
    }

    @PutMapping("/{id}/reject")
    public void reject(@PathVariable Long id) {
        this.service.reject(id);
    }

    @PutMapping("/{id}/cancel")
    public void cancel(@PathVariable Long id) {
        this.service.cancel(id);
    }

    @PutMapping("/{id}/complete")
    public void markAsCompleted(@PathVariable Long id) {
        this.service.markAsCompleted(id);
    }

    @PutMapping("/{id}/wait")
    public void onWait(@PathVariable Long id) {
        this.service.onWait(id);
    }

    @GetMapping("/{reservationId}/review")
    public Optional<ReservationReview> getReservationWithReview(@PathVariable Long reservationId) {
        return this.service.getReservationWithReview(reservationId);
    }

}
