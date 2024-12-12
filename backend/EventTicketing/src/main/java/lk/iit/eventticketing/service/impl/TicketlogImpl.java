package lk.iit.eventticketing.service.impl;

import lk.iit.eventticketing.dto.TicketlogDto;
import lk.iit.eventticketing.model.Ticketlog;
import lk.iit.eventticketing.repo.TicketlogRepo;
import lk.iit.eventticketing.service.TicketlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketlogImpl implements TicketlogService {

    @Autowired
    private TicketlogRepo ticketlogRepo;

    // Fetch all ticket logs and map to DTOs
    public List<TicketlogDto> getAllTicketlogs() {
        return ticketlogRepo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Fetch a single ticket log by ID and map to DTO
    public TicketlogDto getTicketlogById(Long id) {
        Ticketlog ticketlog = ticketlogRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticketlog not found with id: " + id));
        return mapToDto(ticketlog);
    }

    // Map Ticketlog entity to TicketlogDto
    private TicketlogDto mapToDto(Ticketlog ticketlog) {
        return new TicketlogDto(
                ticketlog.getTicketlogId(),
                ticketlog.getProcess(),
                ticketlog.getUserName(),
                ticketlog.getUserId(),
                ticketlog.getEventName(),
                ticketlog.getTicketNo(),
                ticketlog.getEventId()
        );
    }
}
