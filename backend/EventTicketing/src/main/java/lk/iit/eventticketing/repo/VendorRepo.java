package lk.iit.eventticketing.repo;

import lk.iit.eventticketing.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface VendorRepo extends JpaRepository<Vendor, Long> {

    Vendor findByVendorEmail(String email);


}
