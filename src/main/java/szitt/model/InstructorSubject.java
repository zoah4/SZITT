package szitt.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Data
@Table(name = "instruktor_predmet")
public class InstructorSubject implements Serializable{
    @EmbeddedId
    private InstructorSubjectId id;

    @ManyToOne
    @MapsId("instruktorId")
    @JoinColumn(name = "instruktor_id", nullable = false)
    private Instructor instructor;

    @ManyToOne
    @MapsId("predmetId")
    @JoinColumn(name = "predmet_id", nullable = false)
    private Subject subject;

    public InstructorSubject() {}

    public InstructorSubject(Instructor instructor, Subject subject) {
        this.instructor = instructor;
        this.subject = subject;
        this.id = new InstructorSubjectId(instructor.getId(), subject.getId());
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
}
