package com.example.jobProcessor.controller;

import com.example.jobProcessor.dto.JobRequestDto;
import com.example.jobProcessor.dto.JobResponseDto;
import com.example.jobProcessor.service.JobService;
import com.example.jobProcessor.service.JobWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    @Autowired
    JobService jobService;

    @Autowired
    JobWorkerService workerService;

    @PostMapping("/service")
    public JobResponseDto create(@RequestBody JobRequestDto requestDto) {
        return jobService.createJob(requestDto);
    }

}
