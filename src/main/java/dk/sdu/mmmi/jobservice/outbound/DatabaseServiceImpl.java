package dk.sdu.mmmi.jobservice.outbound;

import dk.sdu.mmmi.jobservice.outbound.repository.JobRepository;
import dk.sdu.mmmi.jobservice.service.interfaces.DatabaseService;
import dk.sdu.mmmi.jobservice.service.model.Job;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class DatabaseServiceImpl implements DatabaseService {

    private final JobRepository jobRepository;


    @Override
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public Job getJob(long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public Job updateJob(long id, Job job) {
        job.setUpdatedAt(new Date());
        return jobRepository.save(job);
    }

    @Override
    public void deleteJob(long id) {
        jobRepository.deleteById(id);
    }
}
