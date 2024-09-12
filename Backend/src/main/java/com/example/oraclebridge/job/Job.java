package com.example.oraclebridge.job;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "JOBS")
public class Job {
    @Id
    private String jobId;
    private String jobTitle;
    private Integer minSalary;
    private Integer maxSalary;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String JOB_TITLE) {
        this.jobTitle = JOB_TITLE;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer MIN_SALARY) {
        this.minSalary = MIN_SALARY;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Integer MAX_SALARY) {
        this.maxSalary = MAX_SALARY;
    }
}
