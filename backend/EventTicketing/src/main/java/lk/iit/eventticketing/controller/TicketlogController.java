package lk.iit.eventticketing.controller;

import lk.iit.eventticketing.dto.TicketlogDto;
import lk.iit.eventticketing.service.TicketlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/ticketlogs")
public class TicketlogController {

    @Autowired
    private TicketlogService ticketlogService;

    // Endpoint to fetch all ticket logs
    @GetMapping("/all")
    public List<TicketlogDto> getAllTicketlogs() {
        return ticketlogService.getAllTicketlogs();
    }

    // Endpoint to fetch a single ticket log by ID
    @GetMapping("/{id}")
    public TicketlogDto getTicketlogById(@PathVariable Long id) {
        return ticketlogService.getTicketlogById(id);
    }
}
