package dk.sdu.mmmi.jobservice;

import dk.sdu.mmmi.jobservice.service.model.*;

import java.time.Instant;
import java.util.Date;

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
        Date date = new Date();
        Instant instant = date.toInstant().plusSeconds(86400); // 1 day
        job.setExpiresAt(Date.from(instant));
        job.setUpdatedAt(new java.util.Date());
        return job;
    }

    public static Application createMockApplication() {
        Application application = new Application();
        application.setJob(createMockJob());
        application.setUserId("userIdUUID");
        application.setCreatedAt(new java.util.Date());
        application.setUpdatedAt(new java.util.Date());
        application.setId(1L);
        application.setStatus(ApplicationStatus.PENDING);
        return application;
    }

    public static ApplicationDTO createMockApplicationDTO() {
        ApplicationDTO application = new ApplicationDTO();
        application.setJobId(1L);
        application.setUserId("userIdUUID");
        application.setCreatedAt(new java.util.Date());
        application.setUpdatedAt(new java.util.Date());
        application.setId(1L);
        application.setStatus(ApplicationStatus.PENDING);
        application.setApplication("This is a test application");
        return application;
    }
}
