package com.example.jobProcessor.service;

import com.example.jobProcessor.entity.Job;
import com.example.jobProcessor.entity.JobHistory;
import com.example.jobProcessor.entity.Status;
import com.example.jobProcessor.repository.JobHistoryRepository;
import com.example.jobProcessor.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobWorkerService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobExecutor executor;

    @Scheduled(fixedDelay = 30000)
    public void pickJobs() {
        System.out.println("Scheduler Triggered!");
        List<Job> jobs = jobRepository.findByStatus(Status.QUEUED);
        jobs.forEach(executor::processSingleJob);
    }
}
