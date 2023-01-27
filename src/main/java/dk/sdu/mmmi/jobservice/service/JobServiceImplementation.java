package dk.sdu.mmmi.jobservice.service;

import dk.sdu.mmmi.jobservice.service.interfaces.DatabaseService;
import dk.sdu.mmmi.jobservice.service.interfaces.JobService;
import dk.sdu.mmmi.jobservice.service.model.Job;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImplementation implements JobService {

    private final DatabaseService databaseService;

    @Override
    public Job createJob(Job job) {
        log.info("--> createJob: {}", job);
        job.setCreatedAt(new Date());
        return databaseService.createJob(job);
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
}
