package lk.iit.eventticketing.dto;


public class TicketlogDto {

    private Long ticketlogId;
    private String process;
    private String userName;
    private Long userId;
    private String eventName;
    private Long eventId;

    public TicketlogDto() {}

    public TicketlogDto(Long ticketlogId, String process, String userName, Long userId, String eventName, int ticketsAvailable,Long eventId) {
        this.ticketlogId = ticketlogId;
        this.process = process;
        this.userName = userName;
        this.userId = userId;
        this.eventName = eventName;
        this.eventId = eventId;
    }

    public Long getTicketlogId() {
        return ticketlogId;
    }

    public void setTicketlogId(Long ticketlogId) {
        this.ticketlogId = ticketlogId;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "TicketlogDto{" +
                "ticketlogId=" + ticketlogId +
                ", process='" + process + '\'' +
                ", userName='" + userName + '\'' +
                ", userId=" + userId +
                ", eventName='" + eventName + '\'' +
                ", eventId='" + eventId + '\'' +
                '}';
    }
}
