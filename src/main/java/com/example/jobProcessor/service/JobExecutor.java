package com.example.jobProcessor.service;

import com.example.jobProcessor.entity.Job;
import com.example.jobProcessor.entity.JobHistory;
import com.example.jobProcessor.entity.Status;
import com.example.jobProcessor.repository.JobHistoryRepository;
import com.example.jobProcessor.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class JobExecutor {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobHistoryRepository historyRepository;

    @Autowired
    JobService jobService;

    @Transactional
    public void processSingleJob(Job job) {
        job.setStatus(Status.RUNNING);
        jobRepository.save(job);
        JobHistory history = new JobHistory();
        history.setJobId(job.getId());
        history.setTimestamp(LocalDateTime.now());
        history.setMessage("Job is running.");
        history.setStatus(Status.RUNNING);
        historyRepository.save(history);
        try {
            jobService.executeJob(job);
            job.setStatus(Status.COMPLETED);
            jobRepository.save(job);
            JobHistory history1 = new JobHistory();
            history1.setMessage("Job Completed, executed successfully!");
            history1.setJobId(job.getId());
            history1.setTimestamp(LocalDateTime.now());
            history1.setStatus(Status.COMPLETED);
            historyRepository.save(history1);
        } catch (Exception e) {
            job.setStatus(Status.FAILED);
            jobRepository.save(job);
            jobService.handleRetry(job);
        }
    }
}
