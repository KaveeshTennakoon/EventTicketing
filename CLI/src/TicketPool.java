import java.util.ArrayList;

public class TicketPool {

    private final ArrayList<String> tickets;
    private final int maxCapacity;
    private int ticketCounter; // Tracks the next ticket number

    public TicketPool(int maxCapacity) {
        this.tickets = new ArrayList<>(maxCapacity);
        this.maxCapacity = maxCapacity;
        this.ticketCounter = 1;
    }

    public synchronized void addTickets(int count, int vendorId) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            if (ticketCounter <= maxCapacity) {
                String ticket = "Ticket-" + ticketCounter++;
                tickets.add(ticket);
                System.out.println("Vendor " + vendorId + " added " + ticket);
            } else {
                System.out.println("Vendor " + vendorId + ": Ticket pool is full!");
                break;
            }
        }
    }

    public synchronized String removeTicket(int customerId) throws InterruptedException {
        if (!tickets.isEmpty()) {
            String ticket = tickets.remove(0);
            System.out.println("Customer " + customerId + " bought " + ticket);
            return ticket;
        } else {
            System.out.println("Customer " + customerId + ": No tickets available!");
            return null;
        }
    }

    public synchronized int getTicketCount() {
        return tickets.size();
    }
}
