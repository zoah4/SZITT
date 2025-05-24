package szitt.dto;

public class LoginResponseDTO {
    private Long id;
    private String email;
    private String uloga;

    public LoginResponseDTO(Long id, String email, String uloga) {
        this.email = email;
        this.id = id;
        this.uloga = uloga;
    }
    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }

    public String getUloga() {
        return uloga;
    }
}
