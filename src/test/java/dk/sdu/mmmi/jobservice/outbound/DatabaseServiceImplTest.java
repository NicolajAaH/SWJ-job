package dk.sdu.mmmi.jobservice.outbound;


import dk.sdu.mmmi.jobservice.TestObjects;
import dk.sdu.mmmi.jobservice.outbound.repository.ApplicationRepository;
import dk.sdu.mmmi.jobservice.outbound.repository.JobRepository;
import dk.sdu.mmmi.jobservice.service.interfaces.DatabaseService;
import dk.sdu.mmmi.jobservice.service.model.Application;
import dk.sdu.mmmi.jobservice.service.model.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class DatabaseServiceImplTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    private DatabaseService databaseService;

    @BeforeEach
    public void setUp() {
        databaseService = new DatabaseServiceImpl(jobRepository, applicationRepository);
    }

    @Test
    public void testCreateJob() {
        Job job = TestObjects.createMockJob();

        when(jobRepository.save(job)).thenReturn(job);

        Job result = databaseService.createJob(job);

        verify(jobRepository).save(job);
        assertThat(result).isEqualTo(job);
    }

    @Test
    public void testGetJob() {
        Job job = TestObjects.createMockJob();

        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        Job result = databaseService.getJob(1L);

        verify(jobRepository).findById(1L);
        assertThat(result).isEqualTo(job);
    }

    @Test
    public void testGetJobNotFound() {
        when(jobRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        Job result = databaseService.getJob(1L);

        verify(jobRepository).findById(1L);
        assertThat(result).isNull();
    }

    @Test
    public void testUpdateJob() {
        Job job = TestObjects.createMockJob();
        when(jobRepository.save(job)).thenReturn(job);
        Job updatedJob = databaseService.updateJob(1L, job);
        assertThat(updatedJob).isEqualTo(job);
    }

    @Test
    public void testDeleteJob() {
        doNothing().when(jobRepository).deleteById(anyLong());
        databaseService.deleteJob(1L);
        verify(jobRepository).deleteById(1L);
    }

    @Test
    public void testCreateApplication() {
        Application application = TestObjects.createMockApplication();
        when(applicationRepository.save(any(Application.class))).thenReturn(application);
        Application createdApplication = databaseService.createApplication(application);
        assertThat(createdApplication).isEqualTo(application);
    }

    @Test
    public void testGetApplication() {
        Application application = TestObjects.createMockApplication();
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));

        Application retrievedApplication = databaseService.getApplication(1L);
        assertThat(retrievedApplication).isEqualTo(application);
    }

    @Test
    public void testUpdateApplication() {
        Application application = TestObjects.createMockApplication();
        when(applicationRepository.save(any(Application.class))).thenReturn(application);

        Application updatedApplication = databaseService.updateApplication(1L, application);
        assertThat(updatedApplication).isEqualTo(application);
    }

    @Test
    public void testDeleteApplication() {
        doNothing().when(applicationRepository).deleteById(1L);
        databaseService.deleteApplication(1L);
        verify(applicationRepository).deleteById(1L);
    }
}