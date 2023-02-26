package dk.sdu.mmmi.jobservice.service.interfaces;

import dk.sdu.mmmi.jobservice.service.model.Application;
import dk.sdu.mmmi.jobservice.service.model.Job;

import java.util.List;

public interface DatabaseService {
    Job createJob(Job job);

    Job getJob(long id);

    Job updateJob(long id, Job job);

    void deleteJob(long id);

    Application createApplication(Application application);

    Application getApplication(long id);

    Application updateApplication(long id, Application application);

    void deleteApplication(long id);

    List<Job> getJobsByCompanyId(long id);

    List<Job> getJobs();

    List<Application> getApplicationsByUserId(String userId);

    List<Job> searchJobs(String searchTerm);
}
