package szitt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import szitt.enums.RoleEnum;

@Getter
@Setter
@Entity
@Table(name = "korisnici")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ime", length = 100)
    private String firstname;

    @Column(name = "prezime", length = 100)
    private String lastname;

    @Column(name = "email", length = 150, nullable = false, unique = true)
    private String email;

    @Column(name = "lozinka", length = 255, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "uloga", nullable = false)
    private RoleEnum role;

    @Column(name = "datum_registracije", nullable = false)
    private LocalDateTime dateOfRegistration = LocalDateTime.now();

    public User() {}

    public User(String firstname, String lastname, String email, String password, RoleEnum role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

     public void setId(Long id) {
        this.id = id;
     }

     public String getFirstname() {
        return firstname;
     }

     public void setFirstname(String firstname) {
        this.firstname = firstname;
     }

     public String getLastname() {
        return lastname;
     }

     public void setLastname(String lastname) {
        this.lastname = lastname;
     }

     public String getEmail() {
        return email;
     }

     public void setEmail(String email) {
        this.email = email;
     }

     public String getPassword() {
        return password;
     }

     public void setPassword(String password) {
        this.password = password;
     }

     public RoleEnum getRole() {
        return role;
     }

     public void setRole(RoleEnum role) {
        this.role = role;
     }

     public LocalDateTime getDateOfRegistration() {
        return dateOfRegistration;
     }

     public void setDateOfRegistration(LocalDateTime dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
     }
}
