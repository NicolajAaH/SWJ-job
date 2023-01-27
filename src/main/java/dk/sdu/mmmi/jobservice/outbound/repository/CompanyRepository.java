package dk.sdu.mmmi.jobservice.outbound.repository;

import dk.sdu.mmmi.jobservice.service.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company name(String name);
}
