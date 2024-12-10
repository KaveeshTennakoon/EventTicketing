package lk.iit.eventticketing.response;

public class LoginResponse {

    private String message;
    private Boolean status;
    private String token;
    private String name;
    private long id;

    public LoginResponse() {}

    public LoginResponse(String message, Boolean status, String token, String name, long id) {
        this.message = message;
        this.status = status;
        this.token = token;
        this.name = name;
        this.id = id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
