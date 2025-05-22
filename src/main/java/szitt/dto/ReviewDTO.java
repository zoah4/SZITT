package szitt.dto;

import java.time.LocalDateTime;

public class ReviewDTO {
    ReservationDTO reservationDTO;
    Integer rating;
    String comment;
    LocalDateTime date;

    public ReviewDTO() {}

    public ReviewDTO(ReservationDTO reservationDTO, Integer rating, String comment, LocalDateTime date) {
        this.reservationDTO = reservationDTO;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }
    public ReservationDTO getReservationDTO() {
        return reservationDTO;
    }
    public void setReservationDTO(ReservationDTO reservationDTO) {
        this.reservationDTO = reservationDTO;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
