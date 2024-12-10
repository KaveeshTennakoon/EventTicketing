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

    private Map<Long, Map<Long, CompletableFuture<Void>>> activeTicketAdditionTasks = new ConcurrentHashMap<>();

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

    @Override
    @Transactional
    public synchronized boolean startTicketAddition(TicketlogDto ticketlogDto) throws Exception {
        Ticketpool ticketpool = ticketpoolRepo.findById(ticketlogDto.getEventId())
                .orElseThrow(() -> new Exception("Ticket pool not found"));

        // Get the user ID
        Long userId = ticketlogDto.getUserId();

        // Initialize the map for the ticket pool if not present
        activeTicketAdditionTasks
                .computeIfAbsent(ticketpool.getTicketpoolId(), k -> new ConcurrentHashMap<>());

        // Check if a task is already running for this user and pool
        if (activeTicketAdditionTasks.get(ticketpool.getTicketpoolId()).containsKey(userId)) {
            // If a task is running, create another task for the user (allows multiple threads)
            System.out.println("Ticket addition already in progress, starting a new thread");
        }

        // Create and start an async task for the user
        CompletableFuture<Void> ticketAdditionTask = CompletableFuture.runAsync(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    // Add ticket
                    addTicketToPool(ticketlogDto);

                    // Sleep based on ticket release rate (converted to milliseconds)
                    Thread.sleep((long) (ticketpool.getTicketReleaseRate() * 1000));

                    // Stop if max tickets reached
                    if (ticketpool.getTicketsAvailable() >= ticketpool.getTotalTickets()) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                // Log or handle exception
                e.printStackTrace();
            }
        });

        // Store the task for the specific user in the map
        activeTicketAdditionTasks.get(ticketpool.getTicketpoolId()).put(userId, ticketAdditionTask);
        return true;
    }

    @Override
    @Transactional
    public synchronized boolean stopTicketAddition(Long ticketPoolId, Long userId) {
        Map<Long, CompletableFuture<Void>> userTasks = activeTicketAdditionTasks.get(ticketPoolId);
        if (userTasks != null) {
            CompletableFuture<Void> task = userTasks.get(userId);
            if (task != null) {
                task.cancel(true);
                userTasks.remove(userId);
                return true;
            }
        }
        return false;
    }
}

