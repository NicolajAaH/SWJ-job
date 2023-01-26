package dk.sdu.mmmi.jobservice.outbound.repository;

import dk.sdu.mmmi.jobservice.service.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

}
