package dk.sdu.mmmi.jobservice.service.interfaces;

import dk.sdu.mmmi.jobservice.service.model.Company;

import java.util.List;

public interface CompanyService {
    List<Company> findAll();

    Company create(Company company);

    Company findById(Long id);

    Company update(Long id, Company company);

    void delete(Long id);

    Company findByCompanyName(String name);
}
