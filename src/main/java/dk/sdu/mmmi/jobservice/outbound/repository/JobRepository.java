package dk.sdu.mmmi.jobservice.outbound.repository;

import dk.sdu.mmmi.jobservice.service.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findAllByCompanyId(Long id);

    List<Job> findAllByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String searchTerm, String searchTerm2);
}
