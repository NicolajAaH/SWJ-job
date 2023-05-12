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
import java.util.Date;
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

    /**
     * Create a new job
     * @param job The job to create
     * @return The created job
     */
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        log.info("--> createJob: {}", job);
        Job createdJob = jobService.createJob(job);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    /**
     * Apply for a job
     * @param id The id of the job to apply for
     * @param applicationDTO The application to create
     * @return HttpStatus.CREATED if the application was created, HttpStatus.BAD_REQUEST if the job has expired
     */
    @PostMapping("/{id}/apply")
    public ResponseEntity<Void> applyForJob(@PathVariable("id") long id, @RequestBody ApplicationDTO applicationDTO) {
        log.info("--> applyForJob: {}, id: {}", applicationDTO, id);
        Application application = dtoMapper.applicationDTOToApplication(applicationDTO); //Map DTO to Application
        application.setJob(jobService.getJob(applicationDTO.getJobId())); //Set the job of the application
        if (application.getJob().getExpiresAt().before(new Date())){ //If job expired, it is not possible to apply
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        jobService.applyForJob(id, application);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Get all jobs
     * @param page The page number
     * @param size The page size
     * @return A page of jobs
     */
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

    /**
     * Get a job by id
     * @param id The id of the job
     * @return The job
     */
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(@PathVariable("id") long id) {
        log.info("--> getJob: {}", id);
        Job job = jobService.getJob(id);
        if (job == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    /**
     * Get all applications for a job
     * @param id The id of the job
     * @return A list of applications
     */
    @GetMapping("/{id}/applications")
    public ResponseEntity<List<Application>> getJobApplications(@PathVariable("id") long id) {
        log.info("--> getJob: {}", id);
        List<Application> applications = jobService.getJobApplications(id);
        if (applications == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    /**
     * Get all jobs by company id
     * @param id The id of the company
     * @return A list of jobs
     */
    @GetMapping("/companies/{id}")
    public ResponseEntity<List<Job>> getJobsByCompanyId(@PathVariable("id") long id) {
        log.info("--> getJobsByCompanyId: {}", id);
        List<Job> jobs = jobService.getJobsByCompanyId(id);
        if (jobs == null || jobs.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    /**
     * Update job
     * @param id The id of the job
     * @param job The job to update
     * @return The updated job
     */
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable("id") long id, @RequestBody Job job) {
        log.info("--> updateJob: {}", job);
        Job updatedJob = jobService.updateJob(id, job);
        if (updatedJob == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedJob, HttpStatus.OK);
    }

    /**
     * Delete job
     * @param id The id of the job
     * @return HttpStatus.NO_CONTENT if deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable("id") long id) {
        log.info("--> deleteJob: {}", id);
        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Update application
     * @param id The id of the application
     * @param applicationDTO The application to update
     * @return HttpStatus.NO_CONTENT if updated, HttpStatus.BAD_REQUEST if the id's don't match
     */
    @PutMapping("/application/{id}")
    public ResponseEntity<Void> updateApplication(@PathVariable("id") long id, @RequestBody ApplicationDTO applicationDTO) {
        log.info("--> updateApplication: {}", applicationDTO);
        if(id != applicationDTO.getId()){ //If id's don't match, return bad request
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Application application = dtoMapper.applicationDTOToApplication(applicationDTO); //Map DTO to Application
        Application oldApplication = applicationService.getApplication(id); //Get the old application
        if(oldApplication == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        application.setJob(oldApplication.getJob()); //Set the job of the new application from the old application
        applicationService.updateApplication(id, application);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get applications by user id
     * @param userId The id of the user
     * @return List of applications
     */
    @GetMapping("/applications/{userId}")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByUserId(@PathVariable("userId") String userId) {
        log.info("--> getApplicationsByUserId: {}", userId);
        List<Application> applications = applicationService.getApplicationsByUserId(userId);
        if (applications == null || applications.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        }
        List<ApplicationDTO> applicationDTOS = applications.stream().map(dtoMapper::applicationToApplicationDTO).toList(); //Map applications to DTOs
        return new ResponseEntity<>(applicationDTOS, HttpStatus.OK);
    }

    /**
     * Search for jobs
     * @param searchTerm The search term
     * @param page The page number
     * @param size The page size
     * @return A page of jobs
     */
    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<Page<Job>> searchJobs(@PathVariable("searchTerm") String searchTerm, @RequestParam Integer page, @RequestParam Integer size) {
        log.info("--> searchJobs: {}", searchTerm);
        Pageable pageable = PageRequest.of(page, size);
        Page<Job> jobs = jobService.searchJobs(searchTerm, pageable);

        if (!jobs.hasContent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    /**
     * Filter jobs
     * @param allRequestParams The filter parameters
     * @param page The page number
     * @param size The page size
     * @return A page of jobs
     */
    @GetMapping("/filter")
    public ResponseEntity<Page<Job>> filterJobs(@RequestParam Map<String, String> allRequestParams, @RequestParam Integer page, @RequestParam Integer size) {
        log.info("--> filterJobs: {}", allRequestParams);
        Pageable pageable = PageRequest.of(page, size);
        Page<Job> jobs = jobService.filterJobs(allRequestParams, pageable);
        if (!jobs.hasContent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }
}
