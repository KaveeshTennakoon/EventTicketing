import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class TicketPool {

    private List<String> tickets;
    private int maxTicketCapacity;
    private int currentTicket;
    private int totalTickets;

    public TicketPool(int maxTicketCapacity, int totalTickets) {
        tickets = Collections.synchronizedList(new ArrayList<>());
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalTickets = totalTickets;
        this.currentTicket = 0;

    }

    public synchronized void addTickets(int vendorId){
        if (currentTicket < totalTickets) {
            if (tickets.size()< maxTicketCapacity) {
                currentTicket += 1;
                tickets.add("Ticket-" + currentTicket);
                System.out.println("Ticket-" + currentTicket + " added by Vendor-" + vendorId + ". Current total: " + tickets.size());
            } else {
                System.out.println("Vendor-" + vendorId + " failed to add a ticket to the pool. Ticket pool is full");
            }
        } else {
            System.out.println("Vendor-" + vendorId + " failed to add a ticket to the pool. All the available tickets tickets are added to the pool");
        }
    }

    public synchronized void removeTicket(int customerId){
        if (!tickets.isEmpty()) {
            String ticket = tickets.remove(0);
            System.out.println(ticket + " purchased by Customer-" + customerId + ". Remaining: " + tickets.size());
        } else {
            System.out.println("Customer-" + customerId + " failed to buy the ticket. All tickets are sold out");
        }
    }

    @Override
    public String toString(){
        return  ("Current ticket count in the pool: " + tickets.size());
    }

}