package dk.sdu.mmmi.jobservice.inbound;


import dk.sdu.mmmi.jobservice.service.interfaces.ApplicationService;
import dk.sdu.mmmi.jobservice.service.model.Application;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
@Slf4j
@AllArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        log.info("--> createApplication: {}", application);
        Application createdApplication = applicationService.createApplication(application);
        return new ResponseEntity<>(createdApplication, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplication(@PathVariable("id") long id) {
        log.info("--> getApplication: {}", id);
        Application Application = applicationService.getApplication(id);
        if (Application == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(Application, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable("id") long id, @RequestBody Application application) {
        log.info("--> updateApplication: {}", application);
        Application updatedApplication = applicationService.updateApplication(id, application);
        if (updatedApplication == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedApplication, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable("id") long id) {
        log.info("--> deleteApplication: {}", id);
        applicationService.deleteApplication(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
