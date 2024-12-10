package lk.iit.eventticketing.service.impl;

import jakarta.transaction.Transactional;
import lk.iit.eventticketing.dto.TicketlogDto;
import lk.iit.eventticketing.dto.TicketpoolDto;
import lk.iit.eventticketing.model.Ticketlog;
import lk.iit.eventticketing.model.Ticketpool;
import lk.iit.eventticketing.repo.TicketlogRepo;
import lk.iit.eventticketing.repo.TicketpoolRepo;
import lk.iit.eventticketing.service.TicketpoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Service
public class TicketpoolImpl implements TicketpoolService {

    @Autowired
    private TicketpoolRepo ticketpoolRepo;


    @Autowired
    private TicketlogRepo ticketlogRepo;

    private Map<Long, Future<?>> activeTicketAdditionTasks = new ConcurrentHashMap<>();

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
                ticketpool.getTicketsAvailable(),
                ticketpool.getVendorName()
        );
    }

    @Override
    @Transactional
    public synchronized boolean addTicketToPool(TicketlogDto ticketlogDto) throws Exception {
        Ticketpool ticketpool = ticketpoolRepo.findById(ticketlogDto.getEventId())
                .orElseThrow(() -> new Exception("Ticket pool not found"));

        if (ticketpool.getTicketsAvailable() >= ticketpool.getTotalTickets()) {
            throw new Exception("Ticket pool is full");
        }

        Ticketlog ticketlog = new Ticketlog("added", ticketlogDto.getUserName(), ticketlogDto.getUserId(), ticketpool.getEventName(), ticketpool.getTicketpoolId());
        ticketlogRepo.save(ticketlog);

        ticketpool.setTicketsAvailable(ticketpool.getTicketsAvailable() + 1);
        ticketpoolRepo.save(ticketpool);

        return true;
    }


}
