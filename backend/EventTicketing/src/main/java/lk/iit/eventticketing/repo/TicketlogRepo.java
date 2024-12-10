package lk.iit.eventticketing.repo;

import lk.iit.eventticketing.model.Ticketlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketlogRepo extends JpaRepository<Ticketlog, Long> {
}
