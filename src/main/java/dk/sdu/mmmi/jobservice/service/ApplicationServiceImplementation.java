package dk.sdu.mmmi.jobservice.service;

import dk.sdu.mmmi.jobservice.service.interfaces.ApplicationService;
import dk.sdu.mmmi.jobservice.service.interfaces.DatabaseService;
import dk.sdu.mmmi.jobservice.service.model.Application;
import dk.sdu.mmmi.jobservice.service.model.Job;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationServiceImplementation implements ApplicationService {

    private final DatabaseService databaseService;
    @Override
    public Application createApplication(Application application) {
        log.info("--> createApplication: {}", application);
        return databaseService.createApplication(application);
    }

    @Override
    public Application getApplication(long id) {
        log.info("--> getApplication: {}", id);
        return databaseService.getApplication(id);
    }

    @Override
    public Application updateApplication(long id, Application application) {
        log.info("--> updateApplication: {}", application);
        return databaseService.updateApplication(id, application);
    }

    @Override
    public void deleteApplication(long id) {
        log.info("--> deleteApplication: {}", id);
        databaseService.deleteApplication(id);
    }

    @Override
    public List<Application> getJobApplications(long id) {
        log.info("--> getJobApplications: {}", id);
        Job job = databaseService.getJob(id);
        if(job == null){
            log.error("Job with id {} not found", id);
            return Collections.emptyList();
        }
        return new ArrayList<>(job.getApplications());
    }

    @Override
    public List<Application> getApplicationsByUserId(String userId) {
        log.info("--> getApplicationsByUserId: {}", userId);
        return databaseService.getApplicationsByUserId(userId);
    }
}
