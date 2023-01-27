package dk.sdu.mmmi.jobservice.service;

import dk.sdu.mmmi.jobservice.service.interfaces.CompanyService;
import dk.sdu.mmmi.jobservice.service.interfaces.DatabaseService;
import dk.sdu.mmmi.jobservice.service.model.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImplementation implements CompanyService {

    private final DatabaseService databaseService;

    @Override
    public List<Company> findAll() {
        log.info("--> findAll");
        return databaseService.findAllCompanies();
    }

    @Override
    public Company create(Company company) {
        log.info("--> create: {}", company);
        return databaseService.createCompany(company);
    }

    @Override
    public Company findById(Long id) {
        log.info("--> findById: {}", id);
        return databaseService.findCompanyById(id);
    }

    @Override
    public Company update(Long id, Company company) {
        log.info("--> update: {}", company);
        company.setUpdatedAt(new Date());
        return databaseService.updateCompany(id, company);
    }

    @Override
    public void delete(Long id) {
        log.info("--> delete: {}", id);
        databaseService.deleteCompany(id);
    }
}
