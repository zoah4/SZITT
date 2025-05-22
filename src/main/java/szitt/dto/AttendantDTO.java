package szitt.dto;

public class AttendantDTO {
    private RegisterDTO attendant;
    private int numOfReservations;

    public AttendantDTO() {}

    public AttendantDTO(RegisterDTO attendant, int numOfReservations) {
        this.attendant = attendant;
        this.numOfReservations = numOfReservations;
    }

    public RegisterDTO getAttendant() {
        return attendant;
    }

    public void setAttendant(RegisterDTO attendant) {
        this.attendant = attendant;
    }

    public int getNumOfReservations() {
        return numOfReservations;
    }

    public void setNumOfReservations(int numOfReservations) {
        this.numOfReservations = numOfReservations;
    }
}
