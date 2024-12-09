package lk.iit.eventticketing.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Ticketpool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ticketpoolId;
    @Column(nullable = false)
    private String eventName;
    @Column(nullable = false)
    private BigDecimal ticketPrice;
    @Column(nullable = false)
    private int totalTickets;
    @Column(nullable = false)
    private Date eventDate;
    @Column(nullable = false)
    private int ticketpool;
    @Column(nullable = false)
    private double ticketReleaseRate;
    @Column(nullable = false)
    private double customerRetrievalRate;
    @Column(nullable = false)
    private int ticketsAvailable = 0;
    @JoinColumn(nullable = false)
    private long vendorId;

    public Ticketpool() {}

    public Ticketpool(long ticketpoolId, String eventName, BigDecimal ticketPrice, int totalTickets, Date eventDate, int ticketpool, double ticketReleaseRate, double customerRetrievalRate,long vendorId) {
        this.ticketpoolId = ticketpoolId;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
        this.totalTickets = totalTickets;
        this.eventDate = eventDate;
        this.ticketpool = ticketpool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.vendorId = vendorId;
    }

    public long getTicketpoolId() {
        return ticketpoolId;
    }

    public void setTicketpoolId(long ticketpoolId) {
        this.ticketpoolId = ticketpoolId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public int getTicketpool() {
        return ticketpool;
    }

    public void setTicketpool(int ticketpool) {
        this.ticketpool = ticketpool;
    }

    public double getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(double ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public double getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(double customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Vendor vendor) {
        this.vendorId = vendorId;
    }

    @Override
    public String toString() {
        return "Ticketpool{" +
                "ticketpoolId=" + ticketpoolId +
                ", eventName='" + eventName + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", totalTickets=" + totalTickets +
                ", eventDate=" + eventDate +
                ", ticketpool=" + ticketpool +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", ticketsAvailable=" + ticketsAvailable +
                ", vendor=" + vendorId +
                '}';
    }
}