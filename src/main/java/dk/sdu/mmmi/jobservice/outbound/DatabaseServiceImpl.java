package dk.sdu.mmmi.jobservice.outbound;

import dk.sdu.mmmi.jobservice.outbound.repository.ApplicationRepository;
import dk.sdu.mmmi.jobservice.outbound.repository.JobRepository;
import dk.sdu.mmmi.jobservice.service.interfaces.DatabaseService;
import dk.sdu.mmmi.jobservice.service.model.Application;
import dk.sdu.mmmi.jobservice.service.model.Job;
import dk.sdu.mmmi.jobservice.service.model.JobType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class DatabaseServiceImpl implements DatabaseService {

    private final JobRepository jobRepository;

    private final ApplicationRepository applicationRepository;


    @Override
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public Job getJob(long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public Job updateJob(long id, Job job) {
        job.setUpdatedAt(new Date());
        return jobRepository.save(job);
    }

    @Override
    public void deleteJob(long id) {
        jobRepository.deleteById(id);
    }

    @Override
    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public Application getApplication(long id) {
        return applicationRepository.findById(id).orElse(null);
    }

    @Override
    public Application updateApplication(long id, Application application) {
        application.setUpdatedAt(new Date());
        return applicationRepository.save(application);
    }

    @Override
    public void deleteApplication(long id) {
        applicationRepository.deleteById(id);
    }

    @Override
    public List<Job> getJobsByCompanyId(long id) {
        return jobRepository.findAllByCompanyId(id);
    }

    @Override
    public List<Job> getJobs() {
        return jobRepository.findAll();
    }

    @Override
    public List<Application> getApplicationsByUserId(String userId) {
        return applicationRepository.findAllByUserId(userId);
    }

    @Override
    public List<Job> searchJobs(String searchTerm) {
        //Calling with same parameter twice to search both title and description
        return jobRepository.findAllByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(searchTerm, searchTerm);
    }

    @Override
    public List<Job> filterJobs(Map<String, String> allRequestParams) {
        if (allRequestParams.containsKey("salary") && allRequestParams.containsKey("location") && allRequestParams.containsKey("jobType")) {
            JobType jobType = JobType.valueOf(allRequestParams.get("jobType"));
            return jobRepository.findBySalaryGreaterThanAndLocationContainsIgnoreCaseAndJobType(Double.parseDouble(allRequestParams.get("salary")), allRequestParams.get("location"), jobType);
        } else if (allRequestParams.containsKey("salary") && allRequestParams.containsKey("location")) {
            return jobRepository.findAllBySalaryGreaterThanAndLocationContainsIgnoreCase(Double.parseDouble(allRequestParams.get("salary")), allRequestParams.get("location"));
        } else if (allRequestParams.containsKey("salary") && allRequestParams.containsKey("jobType")) {
            return jobRepository.findAllBySalaryGreaterThanAndJobType(Double.parseDouble(allRequestParams.get("salary")), JobType.valueOf(allRequestParams.get("jobType")));
        } else if (allRequestParams.containsKey("location") && allRequestParams.containsKey("jobType")) {
            return jobRepository.findAllByLocationContainsIgnoreCaseAndJobType(allRequestParams.get("location"), JobType.valueOf(allRequestParams.get("jobType")));
        } else if (allRequestParams.containsKey("salary")) {
            return jobRepository.findAllBySalaryGreaterThan(Double.parseDouble(allRequestParams.get("salary")));
        } else if (allRequestParams.containsKey("location")) {
            return jobRepository.findAllByLocationContainsIgnoreCase(allRequestParams.get("location"));
        } else if (allRequestParams.containsKey("jobType")) {
            return jobRepository.findAllByJobType(JobType.valueOf(allRequestParams.get("jobType")));
        } else {
            return jobRepository.findAll();
        }
    }
}
