package dk.sdu.mmmi.jobservice.service.interfaces;


import dk.sdu.mmmi.jobservice.service.model.Application;

import java.util.List;

public interface ApplicationService {
    Application createApplication(Application application);

    Application getApplication(long id);

    Application updateApplication(long id, Application application);

    void deleteApplication(long id);

    List<Application> getJobApplications(long id);

    List<Application> getApplicationsByUserId(String userId);
}
