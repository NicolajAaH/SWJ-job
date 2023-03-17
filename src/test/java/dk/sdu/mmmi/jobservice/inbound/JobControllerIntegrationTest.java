package dk.sdu.mmmi.jobservice.inbound;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.sdu.mmmi.jobservice.TestObjects;
import dk.sdu.mmmi.jobservice.service.application.JobserviceApplication;
import dk.sdu.mmmi.jobservice.service.interfaces.MqService;
import dk.sdu.mmmi.jobservice.service.model.Job;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JobserviceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class JobControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MqService mqService;


    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateJob() throws Exception {
        Job job = TestObjects.createMockJob();

        doNothing().when(mqService).sendMessage(anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(job)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testCreateJobNoBody() throws Exception {
        Job job = TestObjects.createMockJob();

        doNothing().when(mqService).sendMessage(anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/jobs"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testGetJob() throws Exception {
        long id = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobs/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DirtiesContext
    public void testUpdateJob() throws Exception {
        long id = 2;
        Job job = TestObjects.createMockJob();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/jobs/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(job)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateJobNoBody() throws Exception {
        long id = 2;
        Job job = TestObjects.createMockJob();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/jobs/{id}", id))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DirtiesContext
    public void testDeleteJob() throws Exception {
        long id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/jobs/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteJobNoId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/jobs/"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testGetAllJobs() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobs?page=0&size=10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(2)));
    }

    @Test
    public void testApplyForJob() throws Exception {
        long id = 1;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/jobs/{id}/apply", id)
                        .content(objectMapper.writeValueAsString(TestObjects.createMockApplicationDTO()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void testApplyForJobNoBody() throws Exception {
        long id = 1;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/jobs/{id}/apply", id))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testGetJobApplications() throws Exception {
        long id = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobs/{id}/applications", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetJobsByCompanyId() throws Exception {
        long id = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobs/companies/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DirtiesContext
    public void testUpdateApplication() throws Exception {
        long id = 1;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/jobs/application/{id}", id)
                        .content(objectMapper.writeValueAsString(TestObjects.createMockApplicationDTO()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void testUpdateApplicationBadRequest() throws Exception {
        long id = 2; //Id does not match the object

        mockMvc.perform(MockMvcRequestBuilders.put("/api/jobs/application/{id}", id)
                        .content(objectMapper.writeValueAsString(TestObjects.createMockApplicationDTO()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetApplicationsByUserId() throws Exception {
        String id = "userid123";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobs/applications/{id}", id))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void testGetApplicationsByUserIdNotExisting() throws Exception {
        String id = "fakeuserid123";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobs/applications/{id}", id))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void testSearchJobs() throws Exception {
        String search = "test";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobs/search/{search}", search))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void testSearchJobsNoResults() throws Exception {
        String search = "NoJobs";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobs/search/{search}", search))
                .andExpect(MockMvcResultMatchers.status().isNoContent()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void testFilterJobs() throws Exception {
        double salary = 1000.00;
        String location = "DK";
        String jobType = "BACKEND";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobs/filter?salary={salary}&location={location}&jobType={jobType}", salary, location, jobType))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void testFilterJobsMissingLocation() throws Exception {
        double salary = 1000.00;
        String jobType = "BACKEND";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobs/filter?salary={salary}&jobType={jobType}", salary, jobType))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void testFilterJobsOnlySalary() throws Exception {
        double salary = 1000.00;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobs/filter?salary={salary}", salary))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void testFilterJobsMissingLocationFrontend() throws Exception {
        double salary = 1000.00;
        String jobType = "FRONTEND";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobs/filter?salary={salary}&jobType={jobType}", salary, jobType))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void testFilterJobsEmptyString() throws Exception {
        double salary = 1000.00;
        String jobType = "";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobs/filter?salary={salary}&jobType={jobType}", salary, jobType))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

}
