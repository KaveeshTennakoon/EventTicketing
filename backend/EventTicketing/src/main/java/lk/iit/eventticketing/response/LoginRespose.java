package lk.iit.eventticketing.response;

public class LoginRespose {

    private String message;
    private Boolean status;
    private String token;

    public LoginRespose() {}

    public LoginRespose(String message, Boolean status, String token) {
        this.message = message;
        this.status = status;
        this.token = token;
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

    @Override
    public String toString() {
        return "LoginRespose{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
