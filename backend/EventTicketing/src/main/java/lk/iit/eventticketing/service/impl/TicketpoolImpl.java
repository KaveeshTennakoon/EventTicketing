package lk.iit.eventticketing.service.impl;

import lk.iit.eventticketing.dto.TicketpoolDto;
import lk.iit.eventticketing.model.Ticketpool;
import lk.iit.eventticketing.repo.TicketpoolRepo;
import lk.iit.eventticketing.service.TicketpoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketpoolImpl implements TicketpoolService {

    @Autowired
    private TicketpoolRepo ticketpoolRepo;

    @Override
    public String createTicketpool(TicketpoolDto ticketpoolDto) {

        Ticketpool ticketpool1 = new Ticketpool(
                ticketpoolDto.getTicketpoolId(),
                ticketpoolDto.getEventName(),
                ticketpoolDto.getTicketPrice(),
                ticketpoolDto.getTotalTickets(),
                ticketpoolDto.getEventDate(),
                ticketpoolDto.getTicketpool(),
                ticketpoolDto.getTicketReleaseRate(),
                ticketpoolDto.getCustomerRetrievalRate(),
                ticketpoolDto.getVendorName()
        );

        ticketpoolRepo.save(ticketpool1);
        return ticketpool1.getEventName();

    }

    @Override
    public List<TicketpoolDto> getAllTicketPools() {
        List<Ticketpool> ticketPools = ticketpoolRepo.findAll();
        return ticketPools.stream()
                .map(this::convertToDto)
                .toList();
    }

    private TicketpoolDto convertToDto(Ticketpool ticketpool) {
        return new TicketpoolDto(
                ticketpool.getTicketpoolId(),
                ticketpool.getEventName(),
                ticketpool.getTicketPrice(),
                ticketpool.getTotalTickets(),
                ticketpool.getEventDate(),
                ticketpool.getTicketpool(),
                ticketpool.getTicketReleaseRate(),
                ticketpool.getCustomerRetrievalRate(),
                ticketpool.getVendorName()
        );
    }
}
