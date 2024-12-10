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

    @Async
    @Override
    public boolean startAutoTicketAddition(Long ticketPoolId, String userName, Long userId) throws Exception {
        // Check if a task is already running for this ticket pool
        if (activeTicketAdditionTasks.containsKey(ticketPoolId)) {
            throw new Exception("Ticket addition already in progress for this pool");
        }

        Ticketpool ticketpool = ticketpoolRepo.findById(ticketPoolId)
                .orElseThrow(() -> new Exception("Ticket pool not found"));

        // Create a future to track the ticket addition task
        Future<?> future = executeTicketAddition(ticketPoolId, userName, userId, ticketpool.getTicketReleaseRate());
        activeTicketAdditionTasks.put(ticketPoolId, future);

        return true;
    }

    @Async
    protected Future<?> executeTicketAddition(Long ticketPoolId, String userName, Long userId, double releaseRate) {
        return new AsyncResult<>(CompletableFuture.runAsync(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // Create DTO for ticket addition
                    TicketlogDto ticketlogDto = new TicketlogDto();
                    ticketlogDto.setEventId(ticketPoolId);
                    ticketlogDto.setUserName(userName);
                    ticketlogDto.setUserId(userId);

                    // Add ticket to pool
                    addTicketToPool(ticketlogDto);

                    // Sleep for the specified release rate (in seconds)
                    Thread.sleep((long) (releaseRate * 1000));
                } catch (Exception e) {
                    // Stop if ticket pool is full or any other exception occurs
                    break;
                }
            }
        }));
    }

    @Override
    public boolean stopAutoTicketAddition(Long ticketPoolId) throws Exception {
        Future<?> task = activeTicketAdditionTasks.get(ticketPoolId);
        if (task == null) {
            throw new Exception("No active ticket addition task for this pool");
        }

        // Cancel the task
        task.cancel(true);
        activeTicketAdditionTasks.remove(ticketPoolId);

        return true;
    }

}
