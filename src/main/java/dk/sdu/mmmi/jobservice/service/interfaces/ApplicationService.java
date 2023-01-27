package dk.sdu.mmmi.jobservice.service.interfaces;


import dk.sdu.mmmi.jobservice.service.model.Application;

public interface ApplicationService {
    Application createApplication(Application application);

    Application getApplication(long id);

    Application updateApplication(long id, Application application);

    void deleteApplication(long id);
}
