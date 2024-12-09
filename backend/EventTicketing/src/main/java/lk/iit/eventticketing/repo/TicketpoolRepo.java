package lk.iit.eventticketing.repo;

import lk.iit.eventticketing.model.Ticketpool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketpoolRepo extends JpaRepository<Ticketpool, Long> {
}
