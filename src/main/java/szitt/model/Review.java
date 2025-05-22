package szitt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "recenzije")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "rezervacija_id", unique = true, nullable = false)
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "polaznik_id", nullable = false)
    private Attendant attendant;

    @ManyToOne
    @JoinColumn(name = "instruktor_id", nullable = false)
    private Instructor instructor;

    @Min(1)
    @Max(5)
    @Column(name = "ocjena")
    private Integer rating;

    @Column(name = "komentar")
    private String comment;

    @Column(name = "datum")
    private LocalDateTime date = LocalDateTime.now();

    public Review() {}

    public Review(Reservation reservation, Attendant attendant, Instructor instructor,
                  Integer rating, String comment) {
        this.reservation = reservation;
        this.attendant = attendant;
        this.instructor = instructor;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Attendant getAttendant() {
        return attendant;
    }

    public void setAttendant(Attendant attendant) {
        this.attendant = attendant;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
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
