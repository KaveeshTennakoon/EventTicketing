package lk.iit.eventticketing.controller;

import lk.iit.eventticketing.dto.TicketlogDto;
import lk.iit.eventticketing.dto.TicketpoolDto;
import lk.iit.eventticketing.service.TicketpoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("ticketpool")
public class TicketpoolController {

    @Autowired
    private TicketpoolService ticketpoolService;

    @PostMapping(path = "/create")
    public ResponseEntity<Map<String, String>> createTicketPool(@RequestBody TicketpoolDto ticketPoolDto) {
        String eventName = ticketpoolService.createTicketpool(ticketPoolDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "TicketPool for " + eventName + " created successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TicketpoolDto>> getAllTicketPools() {
        List<TicketpoolDto> ticketPools = ticketpoolService.getAllTicketPools();
        return ResponseEntity.ok(ticketPools);
    }

    @PostMapping(path = "addtickets")
    public ResponseEntity<?> addTicketToPool(@RequestBody TicketlogDto ticketlogDto){
        try {
            boolean added = ticketpoolService.addTicketToPool(ticketlogDto);
            return ResponseEntity.ok().body(Map.of("message", "Ticket added successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

}
