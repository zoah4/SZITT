package szitt.dto;

import szitt.enums.TypeEnum;

public class NotificationDTO {
    RegisterDTO user;
    String content;
    TypeEnum type;

    public NotificationDTO() {}

    public NotificationDTO(RegisterDTO user, String content, TypeEnum type) {
        this.user = user;
        this.content = content;
        this.type = type;
    }

    public RegisterDTO getUser() {
        return user;
    }

    public void setUser(RegisterDTO user) {
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

}
