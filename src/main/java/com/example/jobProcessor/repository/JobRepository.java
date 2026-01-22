package com.example.jobProcessor.repository;

import com.example.jobProcessor.entity.Job;
import com.example.jobProcessor.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByStatus(Status status);
}
