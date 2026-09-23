package es.upm.miw.devops.dto;

public class UserActivePatchRequestDTO {

    private String id;
    private boolean active;

    public UserActivePatchRequestDTO() {
    }

    public UserActivePatchRequestDTO(String id, boolean active) {
        this.id = id;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
