package szitt.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class InstructorSubjectId implements Serializable {
    private Long instruktorId;
    private Long predmetId;

    public InstructorSubjectId() {}

    public InstructorSubjectId(Long instruktorId, Long predmetId) {
        this.instruktorId = instruktorId;
        this.predmetId = predmetId;
    }

    public Long getInstruktorId() {
        return instruktorId;
    }
    public void setInstruktorId(Long instruktorId) {
        this.instruktorId = instruktorId;
    }
    public Long getPredmetId() {
        return predmetId;
    }
    public void setPredmetId(Long predmetId) {
        this.predmetId = predmetId;
    }

}
