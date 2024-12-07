package lk.iit.eventticketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class EventTicketingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventTicketingApplication.class, args);
    }

}
