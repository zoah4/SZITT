package szitt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import szitt.enums.TypeEnum;

@Getter
@Setter
@Entity
@Table(name = "obavijesti")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "korisnik_id")
    private User user;

    @Column(name = "sadrzaj", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "tip", nullable = false)
    private TypeEnum type;

    @Column(name = "kreirano", columnDefinition = "TIMESTAMP")
    private LocalDateTime created = LocalDateTime.now();

    public Notification() {}

    public Notification(User user, String content, TypeEnum type) {
        this.user = user;
        this.content = content;
        this.type = type;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
