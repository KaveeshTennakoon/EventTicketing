import java.util.logging.Logger;

public class Ticket {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private int id;
    private String vendor;
    private String status;

    public Ticket(int id, String vendor) {
        this.id = id;
        this.vendor = vendor;
        this.status = "Available";
    }

    public int getId() {
        return id;
    }

    public String getVendor() {
        return vendor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket-" + id + " added by " + vendor;
    }
}