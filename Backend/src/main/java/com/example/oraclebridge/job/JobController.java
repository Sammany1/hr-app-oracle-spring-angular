package com.example.oraclebridge.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/jobs")
@CrossOrigin(origins = "http://localhost:4200")
public class JobController {
    @Autowired
    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<?> getAllJobs(){
        return handleRequest(() -> {
            List<Job> jobs = jobService.getAllJobs();
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        });
    }

    @GetMapping(value = "id/{id}")
    public ResponseEntity<?> getJobById(@PathVariable String id){
        return handleRequest(() -> {
           Job job = jobService.getJobById(id);
           if(job == null){
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }
           return new ResponseEntity<>(job, HttpStatus.OK);
        });
    }


    @PostMapping(value = "/add",
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addJob(@RequestBody Job job){
        return handleRequest(() -> {
            String newId = job.getJobId();
            if (jobService.getJobById(newId) != null)
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            else{
                jobService.addJob(job);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        });
    }

    @PutMapping(value = "/update/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateJob(@PathVariable String id, @RequestBody Job job){
        return handleRequest(() -> {
            if (jobService.getJobById(id) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            jobService.updateJob(job);
            return new ResponseEntity<>(HttpStatus.OK);
        });
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable String id){
        return handleRequest(() -> {
            if(jobService.getJobById(id) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            jobService.deleteJob(id);
            return new ResponseEntity<>(HttpStatus.OK);
        });
    }

    private ResponseEntity<?> handleRequest(Supplier<ResponseEntity<?>> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("an error occurred");
        }
    }
}
