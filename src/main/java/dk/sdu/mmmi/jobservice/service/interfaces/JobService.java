package dk.sdu.mmmi.jobservice.service.interfaces;

import dk.sdu.mmmi.jobservice.service.model.Application;
import dk.sdu.mmmi.jobservice.service.model.Job;

import java.util.List;

public interface JobService {
    Job createJob(Job job);

    Job getJob(long id);

    Job updateJob(long id, Job job);

    void deleteJob(long id);

    void applyForJob(long id, Application application);

    List<Application> getJobApplications(long id);

    List<Job> getJobsByCompanyId(long id);
}
