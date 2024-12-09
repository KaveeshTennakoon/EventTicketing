package lk.iit.eventticketing.response;

public class LoginResponse {

    private String message;
    private Boolean status;
    private String token;
    private String name;

    public LoginResponse() {}

    public LoginResponse(String message, Boolean status, String token, String name) {
        this.message = message;
        this.status = status;
        this.token = token;
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
