package dk.sdu.mmmi.jobservice.service;

import dk.sdu.mmmi.jobservice.TestObjects;
import dk.sdu.mmmi.jobservice.service.interfaces.ApplicationService;
import dk.sdu.mmmi.jobservice.service.interfaces.DatabaseService;
import dk.sdu.mmmi.jobservice.service.interfaces.JobService;
import dk.sdu.mmmi.jobservice.service.interfaces.RabbitMqService;
import dk.sdu.mmmi.jobservice.service.model.Application;
import dk.sdu.mmmi.jobservice.service.model.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class JobServiceImplementationTest {

    @Mock
    private DatabaseService databaseService;

    @Mock
    private ApplicationService applicationService;

    @Mock
    private RabbitMqService rabbitMqService;

    private JobService jobService;

    @BeforeEach
    public void setUp() {
        jobService = new JobServiceImplementation(databaseService, applicationService, rabbitMqService);
    }

    @Test
    public void testCreateJob() {
        Job job = TestObjects.createMockJob();
        when(databaseService.createJob(any(Job.class))).thenReturn(job);

        Job createdJob = jobService.createJob(TestObjects.createMockJob());

        assertThat(job).isEqualTo(createdJob);
        verify(databaseService, times(1)).createJob(any(Job.class));
        verify(rabbitMqService, times(1)).sendMessage(createdJob);
        verifyNoMoreInteractions(databaseService);
        verifyNoMoreInteractions(rabbitMqService);
        verifyNoInteractions(applicationService);
    }

    @Test
    public void testGetJob() {
        long id = 1L;
        Job job = TestObjects.createMockJob();
        when(databaseService.getJob(id)).thenReturn(job);

        Job retrievedJob = jobService.getJob(id);

        assertThat(job).isEqualTo(retrievedJob);
        verify(databaseService, times(1)).getJob(id);
        verifyNoMoreInteractions(databaseService);
        verifyNoInteractions(rabbitMqService);
        verifyNoInteractions(applicationService);
    }

    @Test
    public void testUpdateJob() {
        long id = 1L;
        Job job = TestObjects.createMockJob();
        when(databaseService.updateJob(id, job)).thenReturn(job);

        Job updatedJob = jobService.updateJob(id, job);

        assertThat(job).isEqualTo(updatedJob);
        verify(databaseService, times(1)).updateJob(id, job);
        verifyNoMoreInteractions(databaseService);
        verifyNoInteractions(rabbitMqService);
        verifyNoInteractions(applicationService);
    }

    @Test
    public void testDeleteJob() {
        long id = 1L;
        jobService.deleteJob(id);
        verify(databaseService, times(1)).deleteJob(id);
        verifyNoMoreInteractions(databaseService);
        verifyNoInteractions(rabbitMqService);
        verifyNoInteractions(applicationService);
    }

    @Test
    public void testApplyForJob() {
        long id = 1L;
        Application application = TestObjects.createMockApplication();
        jobService.applyForJob(id, application);
        verify(applicationService, times(1)).createApplication(application);
        verifyNoInteractions(databaseService);
        verifyNoInteractions(rabbitMqService);
        verifyNoMoreInteractions(applicationService);
    }

    @Test
    public void testGetJobApplications() {
        long id = 1L;
        List<Application> applications = new ArrayList<>(){{
            add(TestObjects.createMockApplication());
        }};

        when(applicationService.getJobApplications(id)).thenReturn(applications);

        List<Application> retrievedApplications = jobService.getJobApplications(id);

        assertEquals(applications, retrievedApplications);
        verify(applicationService, times(1)).getJobApplications(anyLong());
        verifyNoInteractions(databaseService);
        verifyNoInteractions(rabbitMqService);
        verifyNoMoreInteractions(applicationService);
    }

    @Test
    public void testGetJobsByCompanyId() {
        long id = 1L;
        List<Job> jobs = new ArrayList<>(){{
            add(TestObjects.createMockJob());
        }};

        when(jobService.getJobsByCompanyId(id)).thenReturn(jobs);

        List<Job> retrievedJobs = jobService.getJobsByCompanyId(id);

        assertThat(retrievedJobs).isEqualTo(jobs);
        verify(databaseService, times(1)).getJobsByCompanyId(anyLong());
        verifyNoMoreInteractions(databaseService);
        verifyNoInteractions(rabbitMqService);
        verifyNoInteractions(applicationService);
    }

    @Test
    public void testGetJobs() {
        List<Job> jobs = new ArrayList<>(){{
            add(TestObjects.createMockJob());
        }};

        when(jobService.getJobs()).thenReturn(jobs);

        List<Job> retrievedJobs = jobService.getJobs();

        assertThat(retrievedJobs).isEqualTo(jobs);
        verify(databaseService, times(1)).getJobs();
        verifyNoMoreInteractions(databaseService);
        verifyNoInteractions(rabbitMqService);
        verifyNoInteractions(applicationService);
    }
}