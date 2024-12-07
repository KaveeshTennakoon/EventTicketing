import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws InterruptedException {

        configureLogger();

        Scanner scanner = new Scanner(System.in);

        Configuration config = new Configuration();

        System.out.println("\nWelcome to the Real-Time Ticketing System CLI!\n");

        // configuration parameters
        config.setTotalTickets(validateConfig("Total Number of Tickets: "));
        config.setTicketReleaseRate(validateConfig("Ticket Release Rate (s): "));
        config.setCustomerRetrievalRate(validateConfig("Customer Retrieval Rate (s): "));
        boolean valid = false;
        while (!valid) {
            config.setMaxTicketCapacity(validateConfig("Maximum Ticket Capacity: "));
            if (config.getMaxTicketCapacity() > config.getTotalTickets()) {
                System.out.println("Maximum ticket capacity should be less or equal to the total number of tickets");
            }
            else {
                valid = true;
            }
        }

        saveConfig(config);
        Thread.currentThread().sleep(50);

        int numVendors = validateConfig("Enter number of vendors you want in system: ");
        int numCustomers = validateConfig("Enter number of customers you want in system: ");

        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());

        boolean running = true;
        boolean ticketingRunning = false;

        Thread[] vendorThreads = new Thread[numVendors];
        Thread[] customerThreads = new Thread[numCustomers];

        while (running) {
            Thread.currentThread().sleep(200);
            if (!ticketingRunning) {
                System.out.println("\nMenu:");
                System.out.println("1. Start ticketing");
                System.out.println("2. Stop ticketing");
                System.out.println("3. View ticket pool status");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
            }
            int choice = validateConfig("");

            switch (choice) {
                case 1:
                    if (!ticketingRunning) {
                        logger.info("Ticketing process initiated.");
                        ticketingRunning = true;

                        // Start vendor threads
                        for (int i = 0; i < numVendors; i++) {
                            int vendorId = i + 1;
                            vendorThreads[i] = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), vendorId));
                            vendorThreads[i].start();
                        }

                        // Start customer threads
                        for (int i = 0; i < numCustomers; i++) {
                            int customerId = i + 1;
                            customerThreads[i] = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate(), customerId));
                            customerThreads[i].start();
                        }
                    } else {
                        logger.warning("Attempted to start ticketing while already running.");
                    }
                    break;

                case 2:
                    if (ticketingRunning) {
                        logger.info("Ticketing process stopped.");
                        ticketingRunning = false;

                        // Interrupt all threads
                        for (Thread thread : vendorThreads) {
                            if (thread != null) thread.interrupt();
                        }
                        for (Thread thread : customerThreads) {
                            if (thread != null) thread.interrupt();
                        }
                    } else {
                        logger.warning("Attempted to stop ticketing while it was not running.");
                    }
                    break;

                case 3:
                    System.out.println(ticketPool.toString());
                    logger.fine("Displayed ticket pool status.");
                    break;

                case 4:
                    System.out.println("\nExiting...");
                    logger.info("Application exited by user.");
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
                    logger.warning("Invalid menu choice entered.");
            }
        }

        scanner.close();
    }




    // creating a json file to save the configuration information
    private static void saveConfig(Configuration config) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("files/configuration.json")) {
            gson.toJson(config, writer);
            logger.info("Configuration saved to configuration.json.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save configuration: " + e.getMessage());
        }

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

    private static void configureLogger() {
        try {
            FileHandler fileHandler = new FileHandler("files/application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            System.err.println("[Error] Failed to configure logger: " + e.getMessage());
        }
    }
}