public class Customer implements Runnable {

    private TicketPool ticketPool;
    private int customerRetrievalRate;
    private int customerId;


    public Customer(TicketPool ticketPool, int customerRetrievalRate, int customerId) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ticketPool.removeTicket(customerId);
                Thread.sleep(customerRetrievalRate * 1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Customer-" + customerId + " stopped.");
        }
    }

}