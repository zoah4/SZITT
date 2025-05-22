package szitt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "instruktori")
public class Instructor {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "korisnik_id")
    private User user;

    @Column(name = "opis")
    private String description;

    @Column(name = "cijena_po_satu", precision = 6, scale = 2)
    private BigDecimal pricePerHour;

    public Instructor() {
    }

    public Instructor(User user, String description, BigDecimal pricePerHour) {
        this.user = user;
        this.description = description;
        this.pricePerHour = pricePerHour;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
