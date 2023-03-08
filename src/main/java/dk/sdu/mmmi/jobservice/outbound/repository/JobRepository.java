package dk.sdu.mmmi.jobservice.outbound.repository;

import dk.sdu.mmmi.jobservice.service.model.Job;
import dk.sdu.mmmi.jobservice.service.model.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findAllByCompanyId(Long id);

    List<Job> findAllByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String searchTerm, String searchTerm2);

    List<Job> findBySalaryGreaterThanAndLocationContainsIgnoreCaseAndJobType(Double salary, String location, JobType jobType);

    List<Job> findAllBySalaryGreaterThanAndLocationContainsIgnoreCase(Double salary, String location);

    List<Job> findAllBySalaryGreaterThanAndJobType(Double salary, JobType jobType);

    List<Job> findAllByLocationContainsIgnoreCaseAndJobType(String location, JobType jobType);

    List<Job> findAllBySalaryGreaterThan(Double salary);

    List<Job> findAllByLocationContainsIgnoreCase(String location);

    List<Job> findAllByJobType(JobType jobType);
}
