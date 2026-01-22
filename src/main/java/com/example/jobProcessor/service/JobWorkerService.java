package com.example.jobProcessor.service;

import com.example.jobProcessor.entity.Job;
import com.example.jobProcessor.entity.Status;
import com.example.jobProcessor.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobWorkerService {

    @Autowired
    JobService jobService;

    @Autowired
    JobRepository jobRepository;

    @Scheduled(fixedDelay = 30000)
    @Transactional
    public void pickJobs() {
        List<Job> jobs = jobRepository.findByStatus(Status.QUEUED);
        jobs.forEach(job -> {
            job.setStatus(Status.RUNNING);
            jobRepository.save(job);
            try {
                jobService.executeJob(job);
                job.setStatus(Status.COMPLETED);
                jobRepository.save(job);
            } catch (Exception e) {
                job.setStatus(Status.FAILED);
                jobService.handleRetry(job);
            }
        });
    }
}
