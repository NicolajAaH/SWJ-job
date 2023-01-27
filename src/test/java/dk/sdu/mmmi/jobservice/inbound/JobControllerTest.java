package dk.sdu.mmmi.jobservice.inbound;

import dk.sdu.mmmi.jobservice.TestObjects;
import dk.sdu.mmmi.jobservice.service.interfaces.JobService;
import dk.sdu.mmmi.jobservice.service.model.Job;
import dk.sdu.mmmi.jobservice.service.model.JobType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class JobControllerTest {

    @Mock
    private JobService jobService;

    private JobController jobController;



    @BeforeEach
    void setUp() {
        // Create an instance of JobController with the mocked JobService
        jobController = new JobController(jobService);
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
}