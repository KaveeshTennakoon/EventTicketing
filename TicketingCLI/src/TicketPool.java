import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.logging.Logger;

public class TicketPool {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

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

    /*
    * synchronized method to add tickets to the ticket pool list
    * tickets are added by verify the ticket can be added without exceeding the pool limit or total ticket limit
    * Necessary logging prompts are given
     */
    public synchronized void addTickets(int vendorId){
        if (currentTicket < totalTickets) {
            if (tickets.size()< maxTicketCapacity) {
                currentTicket += 1;
                tickets.add("Ticket-" + currentTicket);
                logger.info("Ticket-" + currentTicket + " added by Vendor-" + vendorId + ". Tickets in the pool: " + tickets.size() + "\n");
            } else {
                logger.warning("Vendor-" + vendorId + " failed to add a ticket to the pool. Ticket pool is full\n");
            }
        } else {
            logger.warning("Vendor-" + vendorId + " failed to add a ticket to the pool. All the available tickets are added to the pool\n");
        }
    }

    /*
    * synchronized method to remove tickets from the pool
    * only removes if the tickets list is empty
    * Necessary logging prompts are given
     */
    public synchronized void removeTicket(int customerId){
        if (!tickets.isEmpty()) {
            String ticket = tickets.remove(0);
            logger.info(ticket + " purchased by Customer-" + customerId + ". Tickets in the pool: " + tickets.size() + "\n");
        } else {
            logger.warning("Customer-" + customerId + " failed to buy the ticket. All tickets are sold out\n");
        }
    }

    @Override
    public String toString() {
        return "\nEvent TicketPool Details -" +
                "\nTotal event tickets: " + totalTickets +
                "\nMaximum Ticket Capacity in the pool: " + maxTicketCapacity +
                "\nNumber of tickets in the pool: " + tickets.size();

    }

}