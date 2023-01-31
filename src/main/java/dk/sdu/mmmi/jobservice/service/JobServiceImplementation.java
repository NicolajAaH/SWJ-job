package dk.sdu.mmmi.jobservice.service;

import dk.sdu.mmmi.jobservice.service.interfaces.*;
import dk.sdu.mmmi.jobservice.service.model.Application;
import dk.sdu.mmmi.jobservice.service.model.Job;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImplementation implements JobService {

    private final DatabaseService databaseService;
    private final ApplicationService applicationService;

    private final KafkaService kafkaService;

    @Override
    public Job createJob(Job job) {
        log.info("--> createJob: {}", job);
        job.setCreatedAt(new Date());
        Job createdJob = databaseService.createJob(job);
        kafkaService.sendMessage(createdJob.getId() + "");
        return createdJob;
    }

    @Override
    public Job getJob(long id) {
        log.info("--> getJob: {}", id);
        return databaseService.getJob(id);
    }

    @Override
    public Job updateJob(long id, Job job) {
        log.info("--> updateJob: {}", job);
        job.setUpdatedAt(new Date());
        return databaseService.updateJob(id, job);
    }

    @Override
    public void deleteJob(long id) {
        log.info("--> deleteJob: {}", id);
        databaseService.deleteJob(id);
    }

    @Override
    public void applyForJob(long id, Application application) {
        log.info("--> applyForJob: {}", application);
        applicationService.createApplication(application);
    }

    @Override
    public List<Application> getJobApplications(long id) {
        log.info("--> getJobApplications: {}", id);
        return applicationService.getJobApplications(id);
    }

    @Override
    public List<Job> getJobsByCompanyId(long id) {
        log.info("--> getJobsByCompanyId: {}", id);
        return databaseService.getJobsByCompanyId(id);
    }

    @Override
    public List<Job> getJobs() {
        log.info("--> getJobs");
        return databaseService.getJobs();
    }
}
