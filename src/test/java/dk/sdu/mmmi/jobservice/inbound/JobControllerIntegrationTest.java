package dk.sdu.mmmi.jobservice.inbound;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.sdu.mmmi.jobservice.TestObjects;
import dk.sdu.mmmi.jobservice.service.application.JobserviceApplication;
import dk.sdu.mmmi.jobservice.service.model.Job;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JobserviceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class JobControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateJob() throws Exception {
        Job job = TestObjects.createMockJob();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(job)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetJob() throws Exception {
        long id = 2;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/jobs/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateJob() throws Exception {
        long id = 2;
        Job job = TestObjects.createMockJob();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/jobs/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(job)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteJob() throws Exception {
        long id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/jobs/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
