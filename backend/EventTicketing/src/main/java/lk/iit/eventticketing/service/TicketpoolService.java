package lk.iit.eventticketing.service;

import lk.iit.eventticketing.dto.TicketpoolDto;

import java.util.List;

public interface TicketpoolService {

    String createTicketpool(TicketpoolDto ticketpoolDto);

    List<TicketpoolDto> getAllTicketPools();

}
