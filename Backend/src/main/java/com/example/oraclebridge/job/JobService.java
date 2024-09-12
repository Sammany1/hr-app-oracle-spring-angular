package com.example.oraclebridge.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> getAllJobs(){
        return jobRepository.findAll();
    }
    public Job getJobById(String id){
        return jobRepository.findJobsByJobId(id);
    }
    public Job addJob(Job job){
        return jobRepository.save(job);
    }
    public Job updateJob(Job job){
        return  jobRepository.save(job);
    }
    public void deleteJob(String id){
        jobRepository.deleteById(id);
    }
}
