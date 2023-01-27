package dk.sdu.mmmi.jobservice.service.interfaces;

import dk.sdu.mmmi.jobservice.service.model.Application;
import dk.sdu.mmmi.jobservice.service.model.Company;
import dk.sdu.mmmi.jobservice.service.model.Job;
import dk.sdu.mmmi.jobservice.service.model.User;

import java.util.List;

public interface DatabaseService {
    Job createJob(Job job);

    Job getJob(long id);

    Job updateJob(long id, Job job);

    void deleteJob(long id);

    List<Company> findAllCompanies();

    Company createCompany(Company company);

    Company findCompanyById(Long id);

    Company updateCompany(Long id, Company company);

    void deleteCompany(Long id);

    Application createApplication(Application application);

    Application getApplication(long id);

    Application updateApplication(long id, Application application);

    void deleteApplication(long id);

    User createUser(User user);

    User getUser(long id);

    User updateUser(long id, User user);

    void deleteUser(long id);
}
