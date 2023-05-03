package dk.sdu.mmmi.jobservice.service;

import dk.sdu.mmmi.jobservice.service.interfaces.*;
import dk.sdu.mmmi.jobservice.service.model.Application;
import dk.sdu.mmmi.jobservice.service.model.Job;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImplementation implements JobService {

    private final DatabaseService databaseService;
    private final ApplicationService applicationService;

    private final MqService mqService;

    @Override
    public Job createJob(Job job) {
        log.info("--> createJob: {}", job);
        job.setCreatedAt(new Date());
        Job createdJob = databaseService.createJob(job);
        mqService.sendMessage(createdJob);
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
        if(job.getCompanyId() != null){
            log.warn("Attempt to change company id of job with id {}", id);
            job.setCompanyId(null);
        }
        Job originalJob = databaseService.getJob(id);
        if (originalJob == null) {
            log.error("Job with id {} not found", id);
            return null;
        }
        job.setCompanyId(originalJob.getCompanyId());
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

    @Override
    public void updateApplication(long id, Application application) {
        log.info("--> updateApplication: {}", application);
        application.setUpdatedAt(new Date());
        applicationService.updateApplication(id, application);
    }

    @Override
    public Application getApplication(long id) {
        log.info("--> getApplication: {}", id);
        return applicationService.getApplication(id);
    }

    @Override
    public Page<Job> searchJobs(String searchTerm, Pageable pageable) {
        log.info("--> searchJobs: {}", searchTerm);
        return databaseService.searchJobs(searchTerm, pageable);
    }

    @Override
    public Page<Job> filterJobs(Map<String, String> allRequestParams, Pageable pageable) {
        log.info("--> filterJobs: {}", allRequestParams);
        return databaseService.filterJobs(allRequestParams, pageable);
    }

    @Override
    public Page<Job> getAllJobs(Pageable pageable) {
        log.info("--> getAllJobs: {}", pageable);
        return databaseService.getAllJobs(pageable);
    }
}
