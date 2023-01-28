package dk.sdu.mmmi.jobservice.inbound;

import dk.sdu.mmmi.jobservice.service.interfaces.JobService;
import dk.sdu.mmmi.jobservice.service.model.Application;
import dk.sdu.mmmi.jobservice.service.model.Job;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@AllArgsConstructor
@Slf4j
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        log.info("--> createJob: {}", job);
        Job createdJob = jobService.createJob(job);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<Void> applyForJob(@PathVariable("id") long id, @RequestBody Application application) {
        log.info("--> applyForJob: {}, id: {}", application, id);
        jobService.applyForJob(id, application);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Job>> getJobs() {
        log.info("--> getJobs");
        List<Job> jobs = jobService.getJobs();
        if (jobs == null || jobs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        if (applications == null || applications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<List<Job>> getJobsByCompanyId(@PathVariable("id") long id) {
        log.info("--> getJobsByCompanyId: {}", id);
        List<Job> jobs = jobService.getJobsByCompanyId(id);
        if (jobs == null || jobs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
}
