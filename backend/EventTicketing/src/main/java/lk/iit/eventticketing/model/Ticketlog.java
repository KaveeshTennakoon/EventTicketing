package lk.iit.eventticketing.model;

import jakarta.persistence.*;

@Entity
public class Ticketlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketlogId;
    @Column(nullable = false)
    private String process;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String eventName;
    @Column(nullable = false)
    private Long eventId;

    public Ticketlog() {}

    public Ticketlog( String process, String userName, Long userId, String eventName, Long eventId) {
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
        return "Ticketlog{" +
                "ticketlogId=" + ticketlogId +
                ", process='" + process + '\'' +
                ", userName='" + userName + '\'' +
                ", userId=" + userId +
                ", eventName='" + eventName + '\'' +
                ", eventId='" + eventId + '\'' +
                '}';
    }
}
