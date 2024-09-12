package com.example.oraclebridge.jobhistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobHistoryService {
    @Autowired
    private JobHistoryRepository jobHistoryRepository;

    public JobHistoryService(JobHistoryRepository jobHistoryRepository) {
        this.jobHistoryRepository = jobHistoryRepository;
    }

    public List<JobHistory> getAllJobHistories() {
        return jobHistoryRepository.findAll();
    }

    public JobHistory getJobHistoryById(Integer id) {
        return jobHistoryRepository.findById(id).orElse(null);
    }

    public JobHistory addJobHistory(JobHistory jobHistory) {
        return jobHistoryRepository.save(jobHistory);
    }

    public JobHistory updateJobHistory(JobHistory jobHistory) {
        return jobHistoryRepository.save(jobHistory);
    }

    public void deleteJobHistory(Integer id) {
        jobHistoryRepository.deleteById(id);
    }
}