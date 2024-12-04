import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Real-Time Ticketing System CLI!");

        // Configuration setup with error handling
        int totalTickets = validateConfig("Enter total tickets (initial capacity): ");
        int ticketReleaseRate = validateConfig("Enter ticket release rate (s): ") * 1000;
        int customerRetrievalRate = validateConfig("Enter customer retrieval rate (s): ") * 1000;
        int maxTicketCapacity = validateConfig("Enter max ticket capacity: ");
        int numVendors = validateConfig("Enter number of vendors: ");
        int numCustomers = validateConfig("Enter number of customers: ");

        TicketPool ticketPool = new TicketPool(maxTicketCapacity);

        // Pre-fill the ticket pool
        try {
            ticketPool.addTickets(totalTickets, 1);
        } catch (InterruptedException e) {
            System.err.println("Error adding initial tickets: " + e.getMessage());
        }

        boolean running = true;
        boolean ticketingRunning = false;

        Thread[] vendorThreads = new Thread[numVendors];
        Thread[] customerThreads = new Thread[numCustomers];

        while (running) {
            if (!ticketingRunning) {
                System.out.println("\nMenu:");
                System.out.println("1. Start ticketing");
                System.out.println("2. Stop ticketing");
                System.out.println("3. View ticket pool status");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
            }
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (!ticketingRunning) {
                        System.out.println("\nStarting ticketing...");
                        ticketingRunning = true;

                        // Start vendor threads
                        for (int i = 0; i < numVendors; i++) {
                            int vendorId = i + 1;
                            vendorThreads[i] = new Thread(new Vendor(ticketPool, ticketReleaseRate, vendorId));
                            vendorThreads[i].start();
                        }

                        // Start customer threads
                        for (int i = 0; i < numCustomers; i++) {
                            int customerId = i + 1;
                            customerThreads[i] = new Thread(new Customer(ticketPool, customerRetrievalRate, customerId));
                            customerThreads[i].start();
                        }
                    } else {
                        System.out.println("Ticketing is already running!");
                    }
                    break;

                case 2:
                    if (ticketingRunning) {
                        System.out.println("\nStopping ticketing...");
                        ticketingRunning = false;

                        // Interrupt all threads
                        for (Thread thread : vendorThreads) {
                            if (thread != null) thread.interrupt();
                        }
                        for (Thread thread : customerThreads) {
                            if (thread != null) thread.interrupt();
                        }
                    } else {
                        System.out.println("Ticketing is not running!");
                    }
                    break;

                case 3:
                    System.out.println("\nCurrent ticket count: " + ticketPool.getTicketCount());
                    break;

                case 4:
                    System.out.println("\nExiting...");
                    running = false;
                    ticketingRunning = false;

                    // Stop all threads
                    for (Thread thread : vendorThreads) {
                        if (thread != null) thread.interrupt();
                    }
                    for (Thread thread : customerThreads) {
                        if (thread != null) thread.interrupt();
                    }
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }

        scanner.close();
    }

    private static int validateConfig(String message) {
        Scanner scanner = new Scanner(System.in);
        int value;
        while (true) {
            System.out.print(message);
            try {
                value = scanner.nextInt();
                if (value > 0) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number greater than 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }
        return value;
    }
}
