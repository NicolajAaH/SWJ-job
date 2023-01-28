package dk.sdu.mmmi.jobservice.outbound;

import dk.sdu.mmmi.jobservice.outbound.repository.ApplicationRepository;
import dk.sdu.mmmi.jobservice.outbound.repository.JobRepository;
import dk.sdu.mmmi.jobservice.service.interfaces.DatabaseService;
import dk.sdu.mmmi.jobservice.service.model.Application;
import dk.sdu.mmmi.jobservice.service.model.Job;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
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
}
