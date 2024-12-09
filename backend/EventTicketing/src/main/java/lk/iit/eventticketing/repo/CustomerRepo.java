package lk.iit.eventticketing.repo;

import lk.iit.eventticketing.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Customer findByCustomerEmail(String email);
}
