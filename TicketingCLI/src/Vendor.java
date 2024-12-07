public class Vendor implements Runnable{

    private TicketPool ticketPool;
    private int ticketReleaseRate;
    private int vendorId;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate, int vendorId) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.vendorId = vendorId;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ticketPool.addTickets(vendorId);
                Thread.sleep(ticketReleaseRate * 1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Vendor-" + vendorId + " stopped.");
        }
    }

}