package lk.iit.eventticketing.dto;

import jakarta.persistence.*;
import lk.iit.eventticketing.model.Vendor;

import java.math.BigDecimal;
import java.util.Date;

public class TicketpoolDto {

    private long ticketpoolId;
    private String eventName;
    private BigDecimal ticketPrice;
    private int totalTickets;
    private Date eventDate;
    private int ticketpool;
    private double ticketReleaseRate;
    private double customerRetrievalRate;
    private int ticketsAvailable = 0;
    private String vendorName;

    public TicketpoolDto() {}

    public TicketpoolDto(long ticketpoolId, String eventName, BigDecimal ticketPrice, int totalTickets, Date eventDate, int ticketpool, double ticketReleaseRate, double customerRetrievalRate, String vendorName) {
        this.ticketpoolId = ticketpoolId;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
        this.totalTickets = totalTickets;
        this.eventDate = eventDate;
        this.ticketpool = ticketpool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.vendorName = vendorName;
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

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Override
    public String toString() {
        return "TicketpoolDto{" +
                "ticketpoolId=" + ticketpoolId +
                ", eventName='" + eventName + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", totalTickets=" + totalTickets +
                ", eventDate=" + eventDate +
                ", ticketpool=" + ticketpool +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", ticketsAvailable=" + ticketsAvailable +
                ", vendor=" + vendorName +
                '}';
    }
}

