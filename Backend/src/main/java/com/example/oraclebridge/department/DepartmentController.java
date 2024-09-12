package com.example.oraclebridge.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/departments")
@CrossOrigin(origins = "http://localhost:4200")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<?> getAllDepartments() {
        return handleRequest(() -> {
            List<Department> departments = departmentService.getAllDepartments();
            return new ResponseEntity<>(departments, HttpStatus.OK);
        });
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Integer id) {
        return handleRequest(() -> {
            Department department = departmentService.getDepartmentById(id);
            if (department == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(department, HttpStatus.OK);
        });
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addDepartment(@RequestBody Department department) {
        return handleRequest(() -> {
            Integer newId = department.getDepartmentId();
            if (departmentService.getDepartmentById(newId) != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                departmentService.addDepartment(department);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        });
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDepartment(@PathVariable Integer id, @RequestBody Department department) {
        return handleRequest(() -> {
            if (departmentService.getDepartmentById(id) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            departmentService.updateDepartment(department);
            return new ResponseEntity<>(HttpStatus.OK);
        });
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Integer id) {
        return handleRequest(() -> {
            if (departmentService.getDepartmentById(id) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            departmentService.deleteDepartment(id);
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