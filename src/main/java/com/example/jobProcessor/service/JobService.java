package com.example.jobProcessor.service;

import com.example.jobProcessor.dto.JobRequestDto;
import com.example.jobProcessor.dto.JobResponseDto;
import com.example.jobProcessor.entity.Job;
import com.example.jobProcessor.entity.Status;
import com.example.jobProcessor.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobService {

    @Autowired
    JobRepository jobRepository;

    //DTO -> Entity
    private Job toEntity(JobRequestDto requestDto) {
        Job job = new Job();
        job.setType(requestDto.getType());
        job.setPayLoad(requestDto.getPayload());
        return job;
    }

    //Entity -> DTO
    private JobResponseDto toDto(Job job) {
        return new JobResponseDto(job.getId(), job.getType(), job.getStatus(), job.getRetryCount(), job.getPayLoad());
    }

    public JobResponseDto createJob(JobRequestDto requestDto) {
        Job job = toEntity(requestDto);
        job.setStatus(Status.QUEUED);
        job.setRetryCount(0);
        Job saved = jobRepository.save(job);
        return toDto(saved);
    }

    public void handleRetry(Job job) {
        int retry = job.getRetryCount();
        if(retry < 3) {
            job.setStatus(Status.QUEUED);
            retry++;
            job.setRetryCount(retry);
            jobRepository.save(job);
        } else {
            job.setStatus(Status.FAILED);
            job.setRetryCount(retry);
            jobRepository.save(job);
        }
    }

    public void executeJob(Job job) {
        switch (job.getType()) {
            case EMAIL -> sendEmail(job.getPayLoad());
            case REPORT -> generateReport(job.getPayLoad());
            case CLEANUP -> cleanup(job.getPayLoad());
            case null, default -> throw new IllegalStateException("Wrong Type");
        }
        if(Math.random() > 0.7) {
            throw new RuntimeException("Simulated Failure");
        }
    }

    private void cleanup(String payLoad) {
    }

    private void generateReport(String payLoad) {
    }

    private void sendEmail(String payLoad) {
    }
}
