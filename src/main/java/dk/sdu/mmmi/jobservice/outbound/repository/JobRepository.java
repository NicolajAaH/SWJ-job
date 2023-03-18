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

    Page<Job> findAllByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByExpiresAtDesc(String searchTerm, String searchTerm2, Pageable pageable);

    Page<Job> findBySalaryGreaterThanAndLocationContainsIgnoreCaseAndJobTypeOrderByExpiresAtDesc(Double salary, String location, JobType jobType, Pageable pageable);

    Page<Job> findAllBySalaryGreaterThanAndLocationContainsIgnoreCaseOrderByExpiresAtDesc(Double salary, String location, Pageable pageable);

    Page<Job> findAllBySalaryGreaterThanAndJobTypeOrderByExpiresAtDesc(Double salary, JobType jobType, Pageable pageable);

    Page<Job> findAllByLocationContainsIgnoreCaseAndJobTypeOrderByExpiresAtDesc(String location, JobType jobType, Pageable pageable);

    Page<Job> findAllBySalaryGreaterThanOrderByExpiresAtDesc(Double salary, Pageable pageable);

    Page<Job> findAllByLocationContainsIgnoreCaseOrderByExpiresAtDesc(String location, Pageable pageable);

    Page<Job> findAllByJobTypeOrderByExpiresAtDesc(JobType jobType, Pageable pageable);

    Page<Job> findAllByOrderByExpiresAtDesc(Pageable pageable);
}
