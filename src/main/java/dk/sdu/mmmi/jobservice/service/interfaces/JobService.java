package dk.sdu.mmmi.jobservice.service.interfaces;

import dk.sdu.mmmi.jobservice.service.model.Application;
import dk.sdu.mmmi.jobservice.service.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface JobService {
    Job createJob(Job job);

    Job getJob(long id);

    Job updateJob(long id, Job job);

    void deleteJob(long id);

    void applyForJob(long id, Application application);

    List<Application> getJobApplications(long id);

    List<Job> getJobsByCompanyId(long id);

    List<Job> getJobs();

    void updateApplication(long id, Application application);

    Application getApplication(long id);

    List<Job> searchJobs(String searchTerm);

    List<Job> filterJobs(Map<String, String> allRequestParams);

    Page<Job> getAllJobs(Pageable pageable);
}
