package szitt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import szitt.enums.LocationEnum;
import szitt.enums.StatusEnum;

@Getter
@Setter
@Entity
@Table(name = "rezervacije")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "polaznik_id", nullable = false)
    private Attendant attendant;

    @ManyToOne
    @JoinColumn(name = "instruktor_id", nullable = false)
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "predmet_id", nullable = false)
    private Subject subject;

    @Column(name = "datum_od", nullable = false)
    private LocalDateTime dateFrom;

    @Column(name = "datum_do", nullable = false)
    private LocalDateTime dateTo;

    @Column(name = "trajanje", nullable = false)
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "lokacija", nullable = false)
    private LocationEnum location;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEnum status = StatusEnum.NA_CEKANJU;

    @Column(name = "kreirano")
    private LocalDateTime created = LocalDateTime.now();

    public Reservation() {}

    public Reservation(Attendant attendant, Instructor instructor, Subject subject,
                       LocalDateTime dateFrom, LocalDateTime dateTo, Integer duration,
                       LocationEnum location, StatusEnum status) {
        this.attendant = attendant;
        this.instructor = instructor;
        this.subject = subject;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.duration = duration;
        this.location = location;
        this.status = status;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocationEnum getLocation() {
        return location;
    }

    public void setLocation(LocationEnum location) {
        this.location = location;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
