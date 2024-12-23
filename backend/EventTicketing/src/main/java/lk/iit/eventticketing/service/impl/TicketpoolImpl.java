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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class TicketpoolImpl implements TicketpoolService {

    @Autowired
    private TicketpoolRepo ticketpoolRepo;


    @Autowired
    private TicketlogRepo ticketlogRepo;

    private Map<Long, Map<Long, AtomicBoolean>> ticketAdditionStopFlags = new ConcurrentHashMap<>();

    private Map<Long, Map<Long, AtomicBoolean>> ticketBuyStopFlags = new ConcurrentHashMap<>();

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

        // Check if tickets can be added based on the conditions
        if (ticketpool.getTicketAdd() >= ticketpool.getTotalTickets()) {
            throw new Exception("Ticket pool is full");
        }
        if (ticketpool.getTicketsAvailable() >= ticketpool.getTicketpool()) {
            throw new Exception("No more tickets can be added to the pool");
        }

        // Add ticket to the log
        Ticketlog ticketlog = new Ticketlog("added", ticketlogDto.getUserName(), ticketlogDto.getUserId(),
                ticketpool.getEventName(), ticketpool.getTicketpoolId(), ticketpool.getTicketAdd()+1);
        ticketlogRepo.save(ticketlog);

        // Update the ticket pool with new values
        ticketpool.setTicketsAvailable(ticketpool.getTicketsAvailable() + 1);  // Increase available tickets
        ticketpool.setTicketAdd(ticketpool.getTicketAdd() + 1);                // Increase added tickets
        ticketpoolRepo.save(ticketpool);

        return true;
    }

    @Override
    @Transactional
    public synchronized boolean startTicketAddition(TicketlogDto ticketlogDto) throws Exception {
        Ticketpool ticketpool = ticketpoolRepo.findById(ticketlogDto.getEventId())
                .orElseThrow(() -> new Exception("Ticket pool not found"));

        Long userId = ticketlogDto.getUserId();
        Long ticketPoolId = ticketpool.getTicketpoolId();

        // Initialize stop flags for this ticket pool if not present
        ticketAdditionStopFlags
                .computeIfAbsent(ticketPoolId, k -> new ConcurrentHashMap<>());

        // Create an atomic boolean to control the thread
        AtomicBoolean stopFlag = new AtomicBoolean(false);
        ticketAdditionStopFlags.get(ticketPoolId).put(userId, stopFlag);

        // Create and start an async task for the user
        CompletableFuture<Void> ticketAdditionTask = CompletableFuture.runAsync(() -> {
            try {
                while (!stopFlag.get() && !Thread.currentThread().isInterrupted()) {
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
                e.printStackTrace();
            } finally {
                // Cleanup
                ticketAdditionStopFlags.get(ticketPoolId).remove(userId);
            }
        });

        return true;
    }


    @Override
    @Transactional
    public synchronized boolean stopTicketAddition(Long ticketPoolId, Long userId) throws Exception {
        // Check if the pool and user exist in the stop flags
        if (ticketAdditionStopFlags.containsKey(ticketPoolId)) {
            Map<Long, AtomicBoolean> userStopFlags = ticketAdditionStopFlags.get(ticketPoolId);

            if (userStopFlags != null && userStopFlags.containsKey(userId)) {
                // Set the stop flag to true
                AtomicBoolean stopFlag = userStopFlags.get(userId);
                stopFlag.set(true);

                // Remove the stop flag
                userStopFlags.remove(userId);

                // Clean up the pool map if no flags remain
                if (userStopFlags.isEmpty()) {
                    ticketAdditionStopFlags.remove(ticketPoolId);
                }

                return true;
            }
        }

        throw new Exception("No active ticket addition task found for this user in this pool");
    }

    @Override
    @Transactional
    public synchronized boolean buyTicketFromPool(TicketlogDto ticketlogDto) throws Exception {
        Ticketpool ticketpool = ticketpoolRepo.findById(ticketlogDto.getEventId())
                .orElseThrow(() -> new Exception("Ticket pool not found"));

        if (ticketpool.getTicketsAvailable() <=0) {
            throw new Exception("All Tickets are sold");
        }
        else {
            Ticketlog ticketlog = new Ticketlog("bought", ticketlogDto.getUserName(), ticketlogDto.getUserId(), ticketpool.getEventName(), ticketpool.getTicketpoolId(), ticketpool.getTicketBuy()+1);
            ticketlogRepo.save(ticketlog);

            ticketpool.setTicketsAvailable(ticketpool.getTicketsAvailable() - 1);
            ticketpool.setTicketBuy(ticketpool.getTicketBuy() + 1);
            ticketpoolRepo.save(ticketpool);
        }

        return true;
    }

    @Override
    @Transactional
    public synchronized boolean startTicketBuy(TicketlogDto ticketlogDto) throws Exception {
        Ticketpool ticketpool = ticketpoolRepo.findById(ticketlogDto.getEventId())
                .orElseThrow(() -> new Exception("Ticket pool not found"));

        Long userId = ticketlogDto.getUserId();
        Long ticketPoolId = ticketpool.getTicketpoolId();

        // Initialize stop flags for this ticket pool if not present
        ticketBuyStopFlags
                .computeIfAbsent(ticketPoolId, k -> new ConcurrentHashMap<>());

        // Create an atomic boolean to control the thread
        AtomicBoolean stopFlag = new AtomicBoolean(false);
        ticketBuyStopFlags.get(ticketPoolId).put(userId, stopFlag);

        // Create and start an async task for the user
        CompletableFuture<Void> ticketBuyTask = CompletableFuture.runAsync(() -> {
            try {
                while (!stopFlag.get() && !Thread.currentThread().isInterrupted()) {
                    // Buy ticket
                    buyTicketFromPool(ticketlogDto);

                    // Sleep based on ticket purchase rate (converted to milliseconds)
                    Thread.sleep((long) (ticketpool.getCustomerRetrievalRate() * 1000));

                    // Stop if no tickets are available
                    if (ticketpool.getTicketsAvailable() <= 0) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Cleanup
                ticketBuyStopFlags.get(ticketPoolId).remove(userId);
            }
        });

        return true;
    }

    @Override
    @Transactional
    public synchronized boolean stopTicketBuy(Long ticketPoolId, Long userId) throws Exception {
        // Check if the pool and user exist in the stop flags
        if (ticketBuyStopFlags.containsKey(ticketPoolId)) {
            Map<Long, AtomicBoolean> userStopFlags = ticketBuyStopFlags.get(ticketPoolId);

            if (userStopFlags != null && userStopFlags.containsKey(userId)) {
                // Set the stop flag to true
                AtomicBoolean stopFlag = userStopFlags.get(userId);
                stopFlag.set(true);

                // Remove the stop flag
                userStopFlags.remove(userId);

                // Clean up the pool map if no flags remain
                if (userStopFlags.isEmpty()) {
                    ticketBuyStopFlags.remove(ticketPoolId);
                }

                return true;
            }
        }

        throw new Exception("No active ticket buy task found for this user in this pool");
    }


}

