package dk.sdu.mmmi.jobservice.service.interfaces;

import dk.sdu.mmmi.jobservice.service.model.Job;

public interface JobService {
    Job createJob(Job job);

    Job getJob(long id);

    Job updateJob(long id, Job job);

    void deleteJob(long id);
}
