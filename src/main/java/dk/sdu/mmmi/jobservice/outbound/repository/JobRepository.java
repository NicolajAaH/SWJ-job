package dk.sdu.mmmi.jobservice.outbound.repository;

import dk.sdu.mmmi.jobservice.service.model.Job;
import dk.sdu.mmmi.jobservice.service.model.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findAllByCompanyId(Long id);

    Page<Job> findAllByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String searchTerm, String searchTerm2, Pageable pageable);

    Page<Job> findBySalaryGreaterThanAndLocationContainsIgnoreCaseAndJobType(Double salary, String location, JobType jobType, Pageable pageable);

    Page<Job> findAllBySalaryGreaterThanAndLocationContainsIgnoreCase(Double salary, String location, Pageable pageable);

    Page<Job> findAllBySalaryGreaterThanAndJobType(Double salary, JobType jobType, Pageable pageable);

    Page<Job> findAllByLocationContainsIgnoreCaseAndJobType(String location, JobType jobType, Pageable pageable);

    Page<Job> findAllBySalaryGreaterThan(Double salary, Pageable pageable);

    Page<Job> findAllByLocationContainsIgnoreCase(String location, Pageable pageable);

    Page<Job> findAllByJobType(JobType jobType, Pageable pageable);
}
