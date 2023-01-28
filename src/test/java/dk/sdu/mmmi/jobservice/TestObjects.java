package dk.sdu.mmmi.jobservice;

import dk.sdu.mmmi.jobservice.service.model.Job;
import dk.sdu.mmmi.jobservice.service.model.JobType;

public class TestObjects {

    public static Job createMockJob(){
        Job job = new Job();
        job.setTitle("Test Title");
        job.setDescription("This is a test job");
        job.setLocation("Test City");
        job.setSalary(100000.00);
        job.setJobType(JobType.BACKEND);
        job.setCreatedAt(new java.util.Date());
        job.setCompanyId(1L);
        job.setId(1L);
        job.setExpiresAt(new java.util.Date());
        job.setUpdatedAt(new java.util.Date());
        return job;
    }

}
