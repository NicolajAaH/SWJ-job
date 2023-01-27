package dk.sdu.mmmi.jobservice.service;

import dk.sdu.mmmi.jobservice.service.interfaces.ApplicationService;
import dk.sdu.mmmi.jobservice.service.interfaces.DatabaseService;
import dk.sdu.mmmi.jobservice.service.model.Application;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
