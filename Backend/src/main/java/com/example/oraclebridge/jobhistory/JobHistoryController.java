package com.example.oraclebridge.jobhistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/jobhistory")
@CrossOrigin(origins = "http://localhost:4200")
public class JobHistoryController {
    @Autowired
    private JobHistoryService jobHistoryService;

    public JobHistoryController(JobHistoryService jobHistoryService) {
        this.jobHistoryService = jobHistoryService;
    }

    @GetMapping
    public ResponseEntity<?> getAllJobHistories() {
        return handleRequest(() -> {
            List<JobHistory> jobHistories = jobHistoryService.getAllJobHistories();
            return new ResponseEntity<>(jobHistories, HttpStatus.OK);
        });
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<?> getJobHistoryById(@PathVariable Integer id) {
        return handleRequest(() -> {
            JobHistory jobHistory = jobHistoryService.getJobHistoryById(id);
            if (jobHistory == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(jobHistory, HttpStatus.OK);
        });
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addJobHistory(@RequestBody JobHistory jobHistory) {
        return handleRequest(() -> {
            Integer newId = jobHistory.getEmployeeId();
            if (jobHistoryService.getJobHistoryById(newId) != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                jobHistoryService.addJobHistory(jobHistory);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        });
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateJobHistory(@PathVariable Integer id, @RequestBody JobHistory jobHistory) {
        return handleRequest(() -> {
            if (jobHistoryService.getJobHistoryById(id) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            jobHistoryService.updateJobHistory(jobHistory);
            return new ResponseEntity<>(HttpStatus.OK);
        });
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteJobHistory(@PathVariable Integer id) {
        return handleRequest(() -> {
            if (jobHistoryService.getJobHistoryById(id) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            jobHistoryService.deleteJobHistory(id);
            return new ResponseEntity<>(HttpStatus.OK);
        });
    }

    private ResponseEntity<?> handleRequest(Supplier<ResponseEntity<?>> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}