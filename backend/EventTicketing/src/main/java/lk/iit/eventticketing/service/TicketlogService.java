package lk.iit.eventticketing.service;

import lk.iit.eventticketing.dto.TicketlogDto;

import java.util.List;

public interface TicketlogService {

    List<TicketlogDto> getAllTicketlogs();

    TicketlogDto getTicketlogById(Long id);
}
