package dk.sdu.mmmi.jobservice.inbound;

import dk.sdu.mmmi.jobservice.service.interfaces.ApplicationService;
import dk.sdu.mmmi.jobservice.service.interfaces.JobService;
import dk.sdu.mmmi.jobservice.service.model.Application;
import dk.sdu.mmmi.jobservice.service.model.ApplicationDTO;
import dk.sdu.mmmi.jobservice.service.model.Job;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
@AllArgsConstructor
@Slf4j
public class JobController {

    private final JobService jobService;

    private final ApplicationService applicationService;

    private final DTOMapper dtoMapper = DTOMapper.INSTANCE;

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        log.info("--> createJob: {}", job);
        Job createdJob = jobService.createJob(job);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<Void> applyForJob(@PathVariable("id") long id, @RequestBody ApplicationDTO applicationDTO) {
        log.info("--> applyForJob: {}, id: {}", applicationDTO, id);
        Application application = dtoMapper.applicationDTOToApplication(applicationDTO);
        application.setJob(jobService.getJob(applicationDTO.getJobId()));
        jobService.applyForJob(id, application);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Job>> getJobs(@RequestParam Integer page, @RequestParam Integer size) {
        log.info("--> getJobs page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Job> jobs = jobService.getAllJobs(pageable);

        if (!jobs.hasContent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(@PathVariable("id") long id) {
        log.info("--> getJob: {}", id);
        Job job = jobService.getJob(id);
        if (job == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @GetMapping("/{id}/applications")
    public ResponseEntity<List<Application>> getJobApplications(@PathVariable("id") long id) {
        log.info("--> getJob: {}", id);
        List<Application> applications = jobService.getJobApplications(id);
        if (applications == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<List<Job>> getJobsByCompanyId(@PathVariable("id") long id) {
        log.info("--> getJobsByCompanyId: {}", id);
        List<Job> jobs = jobService.getJobsByCompanyId(id);
        if (jobs == null || jobs.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable("id") long id, @RequestBody Job job) {
        log.info("--> updateJob: {}", job);
        Job updatedJob = jobService.updateJob(id, job);
        if (updatedJob == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedJob, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable("id") long id) {
        log.info("--> deleteJob: {}", id);
        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/application/{id}")
    public ResponseEntity<Void> updateApplication(@PathVariable("id") long id, @RequestBody ApplicationDTO applicationDTO) {
        log.info("--> updateApplication: {}", applicationDTO);
        if(id != applicationDTO.getId()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Application application = dtoMapper.applicationDTOToApplication(applicationDTO);
        Application oldApplication = applicationService.getApplication(id);
        if(oldApplication == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        application.setJob(oldApplication.getJob());
        applicationService.updateApplication(id, application);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/applications/{userId}")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByUserId(@PathVariable("userId") String userId) {
        log.info("--> getApplicationsByUserId: {}", userId);
        List<Application> applications = applicationService.getApplicationsByUserId(userId);
        if (applications == null || applications.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        }
        List<ApplicationDTO> applicationDTOS = applications.stream().map(dtoMapper::applicationToApplicationDTO).toList();
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);
    }

    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<List<Job>> searchJobs(@PathVariable("searchTerm") String searchTerm) {
        log.info("--> searchJobs: {}", searchTerm);
        List<Job> jobs = jobService.searchJobs(searchTerm);
        if (jobs == null || jobs.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Job>> filterJobs(@RequestParam Map<String, String> allRequestParams) {
        log.info("--> filterJobs: {}", allRequestParams);
        List<Job> jobs = jobService.filterJobs(allRequestParams);
        if (jobs == null || jobs.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        }
        log.info("Found {} jobs", jobs.size());
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }
}
