package lk.iit.eventticketing.response;

public class LoginRespose {

    private String message;
    private Boolean status;

    public LoginRespose() {}

    public LoginRespose(String message, Boolean status) {
        this.message = message;
        this.status = status;
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

    @Override
    public String toString() {
        return "LoginRespose{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
