package szitt.dto;

import jdk.jshell.Snippet;

import java.math.BigDecimal;

public class InstructorDTO {
    private RegisterDTO instructor;
    private String description;
    private BigDecimal pricePerHour;

    public InstructorDTO() {}

    public InstructorDTO(RegisterDTO instructor, String description, BigDecimal pricePerHour) {
        this.instructor = instructor;
        this.description = description;
        this.pricePerHour = pricePerHour;
    }

    public RegisterDTO getInstructor() {
        return instructor;
    }

    public void setInstructor(RegisterDTO instructor) {
        this.instructor = instructor;
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

