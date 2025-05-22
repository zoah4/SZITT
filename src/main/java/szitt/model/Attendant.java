package szitt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "polaznici")
public class Attendant {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "korisnik_id")
    private User user;

    @Column(name = "broj_rezervacija")
    private int numOfReservations = 0;

    public Attendant() {
    }

    public Attendant(User user, int numOfReservations) {
        this.user = user;
        this.numOfReservations = numOfReservations;
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

    public int getNumOfReservations() {
        return numOfReservations;
    }

    public void setNumOfReservations(int numOfReservations) {
        this.numOfReservations = numOfReservations;
    }
}
