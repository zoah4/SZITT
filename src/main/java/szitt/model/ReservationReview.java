package szitt.model;

public class ReservationReview {
    Reservation reservation;
    Review review;

    public ReservationReview() {}

    public ReservationReview(Reservation reservation, Review review) {
        this.reservation = reservation;
        this.review = review;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
