package dk.sdu.mmmi.jobservice.service;

import dk.sdu.mmmi.jobservice.TestObjects;
import dk.sdu.mmmi.jobservice.service.interfaces.ApplicationService;
import dk.sdu.mmmi.jobservice.service.interfaces.DatabaseService;
import dk.sdu.mmmi.jobservice.service.model.Application;
import dk.sdu.mmmi.jobservice.service.model.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ApplicationServiceImplementationTest {
    @Mock
    private DatabaseService databaseService;

    private ApplicationService applicationService;

    private Application application = TestObjects.createMockApplication();
    private Job job = TestObjects.createMockJob();

    @BeforeEach
    public void setUp() {
        applicationService = new ApplicationServiceImplementation(databaseService);
    }

    @Test
    public void createApplication() {
        when(databaseService.createApplication(application)).thenReturn(application);
        Application createdApplication = applicationService.createApplication(application);
        assertThat(createdApplication).isEqualTo(application);
        verify(databaseService, times(1)).createApplication(application);
        verifyNoMoreInteractions(databaseService);
    }

    @Test
    public void getApplication() {
        when(databaseService.getApplication(anyLong())).thenReturn(application);
        Application returnedApplication = applicationService.getApplication(1L);
        assertThat(returnedApplication).isEqualTo(application);
        verify(databaseService, times(1)).getApplication(anyLong());
        verifyNoMoreInteractions(databaseService);
    }

    @Test
    public void updateApplication() {
        when(databaseService.updateApplication(anyLong(), any(Application.class))).thenReturn(application);
        Application updatedApplication = applicationService.updateApplication(1L, application);
        assertThat(updatedApplication).isEqualTo(application);
        verify(databaseService, times(1)).updateApplication(anyLong(), any(Application.class));
        verifyNoMoreInteractions(databaseService);
    }

    @Test
    public void deleteApplication() {
        applicationService.deleteApplication(1L);
        verify(databaseService, times(1)).deleteApplication(anyLong());
        verifyNoMoreInteractions(databaseService);
    }

    @Test
    public void getJobApplications() {
        Set<Application> applications = new HashSet<>(){{
            add(application);
        }};
        job.setApplications(applications);
        when(databaseService.getJob(1L)).thenReturn(job);
        List<Application> returnedApplications = applicationService.getJobApplications(1L);
        assertThat(returnedApplications).usingRecursiveFieldByFieldElementComparator().isEqualTo(new ArrayList<>(applications));
        verify(databaseService, times(1)).getJob(anyLong());
        verifyNoMoreInteractions(databaseService);
    }
}
