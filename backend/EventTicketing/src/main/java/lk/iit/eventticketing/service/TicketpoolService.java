package lk.iit.eventticketing.service;

import lk.iit.eventticketing.dto.TicketlogDto;
import lk.iit.eventticketing.dto.TicketpoolDto;

import java.util.List;

public interface TicketpoolService {

    String createTicketpool(TicketpoolDto ticketpoolDto);

    List<TicketpoolDto> getAllTicketPools();

    boolean addTicketToPool(TicketlogDto ticketlogDto) throws Exception;

    boolean startTicketAddition(TicketlogDto ticketlogDto) throws Exception;

    boolean stopTicketAddition(Long ticketPoolId, Long userId);
}
