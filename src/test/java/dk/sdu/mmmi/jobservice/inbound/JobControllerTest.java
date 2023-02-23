package dk.sdu.mmmi.jobservice.inbound;

import dk.sdu.mmmi.jobservice.TestObjects;
import dk.sdu.mmmi.jobservice.service.interfaces.ApplicationService;
import dk.sdu.mmmi.jobservice.service.interfaces.JobService;
import dk.sdu.mmmi.jobservice.service.model.Application;
import dk.sdu.mmmi.jobservice.service.model.ApplicationDTO;
import dk.sdu.mmmi.jobservice.service.model.Job;
import dk.sdu.mmmi.jobservice.service.model.JobType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class JobControllerTest {

    @Mock
    private JobService jobService;

    @Mock
    private ApplicationService applicationService;

    private JobController jobController;



    @BeforeEach
    void setUp() {
        // Create an instance of JobController with the mocked JobService
        jobController = new JobController(jobService, applicationService);
    }

    @Test
    void createJob() {
        // Create a mock Job object
        Job mockJob = TestObjects.createMockJob();

        when(jobService.createJob(any(Job.class))).thenReturn(mockJob);

        // Call the createJob method
        ResponseEntity<Job> response = jobController.createJob(mockJob);
        Job job = response.getBody();

        // Assert that the response is correct
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(job).isNotNull();
        assertThat(job.getId()).isEqualTo(1L);
        assertThat(job.getTitle()).isEqualTo("Test Title");
        assertThat(job.getDescription()).isEqualTo("This is a test job");
        assertThat(job.getLocation()).isEqualTo("Test City");
        assertThat(job.getSalary()).isEqualTo(100000.00);
        assertThat(job.getJobType()).isEqualTo(JobType.BACKEND);
        assertThat(job.getCreatedAt()).isNotNull();
        assertThat(job.getUpdatedAt()).isNotNull();
        assertThat(job.getExpiresAt()).isNotNull();

        // Verify that the jobService.createJob method is called with the mock object
        verify(jobService).createJob(any(Job.class));
        verifyNoMoreInteractions(jobService);
    }

    @Test
    void getJob() {
        Job mockJob = TestObjects.createMockJob();

        when(jobService.getJob(anyLong())).thenReturn(mockJob);

        // Call the getJob method
        ResponseEntity<Job> response = jobController.getJob(1);

        // Assert that the response is correct
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Assert that the response is correct
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Job job = response.getBody();
        assertThat(job).isNotNull();
        assertThat(job.getId()).isEqualTo(1L);
        assertThat(job.getTitle()).isEqualTo("Test Title");
        assertThat(job.getDescription()).isEqualTo("This is a test job");
        assertThat(job.getLocation()).isEqualTo("Test City");
        assertThat(job.getSalary()).isEqualTo(100000.00);
        assertThat(job.getJobType()).isEqualTo(JobType.BACKEND);
        assertThat(job.getCreatedAt()).isNotNull();
        assertThat(job.getUpdatedAt()).isNotNull();
        assertThat(job.getExpiresAt()).isNotNull();

        // Verify that the jobService.getJob method is called with the id
        verify(jobService).getJob(anyLong());
        verifyNoMoreInteractions(jobService);
    }

    @Test
    void updateJob() {
        Job mockJob = TestObjects.createMockJob();
        mockJob.setJobType(JobType.ARCHITECT);
        when(jobService.updateJob(anyLong(), any(Job.class))).thenReturn(mockJob);

        // Call the updateJob method
        ResponseEntity<Job> response = jobController.updateJob(1L, mockJob);
        Job job = response.getBody();
        // Assert that the response is correct
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(job).isNotNull();
        assertThat(job.getId()).isEqualTo(1L);
        assertThat(job.getTitle()).isEqualTo("Test Title");
        assertThat(job.getDescription()).isEqualTo("This is a test job");
        assertThat(job.getLocation()).isEqualTo("Test City");
        assertThat(job.getSalary()).isEqualTo(100000.00);
        assertThat(job.getJobType()).isEqualTo(JobType.ARCHITECT);
        assertThat(job.getCreatedAt()).isNotNull();
        assertThat(job.getUpdatedAt()).isNotNull();
        assertThat(job.getExpiresAt()).isNotNull();

        verify(jobService, times(1)).updateJob(anyLong(), any(Job.class));
        verifyNoMoreInteractions(jobService);
    }

    @Test
    void deleteJob() {
        doNothing().when(jobService).deleteJob(anyLong());

        ResponseEntity<Void> response = jobController.deleteJob(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        verify(jobService, times(1)).deleteJob(anyLong());
        verifyNoMoreInteractions(jobService);
    }

    @Test
    void applyForJob() {
        ApplicationDTO mockJobApplication = TestObjects.createMockApplicationDTO();
        doNothing().when(jobService).applyForJob(anyLong(), any(Application.class));

        ResponseEntity<Void> response = jobController.applyForJob(1L, mockJobApplication);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void getJobs() {
        Job mockJob = TestObjects.createMockJob();
        List<Job> mockJobs = new ArrayList<>();
        mockJobs.add(mockJob);

        when(jobService.getJobs()).thenReturn(mockJobs);

        ResponseEntity<List<Job>> response = jobController.getJobs();
        List<Job> jobs = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jobs).isNotNull();
        assertThat(jobs.size()).isEqualTo(1);
        assertThat(jobs.get(0).getId()).isEqualTo(1L);
        assertThat(jobs.get(0).getTitle()).isEqualTo("Test Title");
        assertThat(jobs.get(0).getDescription()).isEqualTo("This is a test job");
        assertThat(jobs.get(0).getLocation()).isEqualTo("Test City");
        assertThat(jobs.get(0).getSalary()).isEqualTo(100000.00);
        assertThat(jobs.get(0).getJobType()).isEqualTo(JobType.BACKEND);
        assertThat(jobs.get(0).getCreatedAt()).isNotNull();
        assertThat(jobs.get(0).getUpdatedAt()).isNotNull();
        assertThat(jobs.get(0).getExpiresAt()).isNotNull();

        verify(jobService, times(1)).getJobs();
        verifyNoMoreInteractions(jobService);
    }


    @Test
    void getJobApplications() {
        Application mockApplication = TestObjects.createMockApplication();
        List<Application> mockApplications = new ArrayList<>();
        mockApplications.add(mockApplication);

        when(jobService.getJobApplications(anyLong())).thenReturn(mockApplications);

        ResponseEntity<List<Application>> response = jobController.getJobApplications(1L);
        List<Application> applications = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(applications).isNotNull();
        assertThat(applications.size()).isEqualTo(1);
        assertThat(applications.get(0).getId()).isEqualTo(1L);
        assertThat(applications.get(0).getUserId()).isEqualTo("userIdUUID");
        assertThat(applications.get(0).getCreatedAt()).isNotNull();
        assertThat(applications.get(0).getUpdatedAt()).isNotNull();

        verify(jobService, times(1)).getJobApplications(anyLong());
        verifyNoMoreInteractions(jobService);
    }

    @Test
    void getJobsByCompanyId() {
        Job mockJob = TestObjects.createMockJob();
        List<Job> mockJobs = new ArrayList<>();
        mockJobs.add(mockJob);

        when(jobService.getJobsByCompanyId(anyLong())).thenReturn(mockJobs);

        ResponseEntity<List<Job>> response = jobController.getJobsByCompanyId(1L);
        List<Job> jobs = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jobs).isNotNull();
        assertThat(jobs.size()).isEqualTo(1);
        assertThat(jobs.get(0).getId()).isEqualTo(1L);
        assertThat(jobs.get(0).getTitle()).isEqualTo("Test Title");
        assertThat(jobs.get(0).getDescription()).isEqualTo("This is a test job");
        assertThat(jobs.get(0).getLocation()).isEqualTo("Test City");
        assertThat(jobs.get(0).getSalary()).isEqualTo(100000.00);
        assertThat(jobs.get(0).getJobType()).isEqualTo(JobType.BACKEND);
        assertThat(jobs.get(0).getCreatedAt()).isNotNull();
        assertThat(jobs.get(0).getUpdatedAt()).isNotNull();
        assertThat(jobs.get(0).getExpiresAt()).isNotNull();

        verify(jobService, times(1)).getJobsByCompanyId(anyLong());
        verifyNoMoreInteractions(jobService);
    }

}