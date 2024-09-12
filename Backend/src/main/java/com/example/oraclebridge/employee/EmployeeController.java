package com.example.oraclebridge.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(){
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Employee> getEmployeeByID(@PathVariable Long id){
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if(employee != null)
                return new ResponseEntity<>(employee, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Employee>> getEmployeeByFirstName(@PathVariable String name) {
        try{
            List<Employee> employees = employeeService.findByFirstName(name);
            if (employees != null)
                return new ResponseEntity<>(employees, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email){
        try{
            Employee employee = employeeService.findByEmail(email);
            if (employee != null)
                return new ResponseEntity<>(employee, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value= "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        try{
            Long newId = employee.getEmployeeId();
            String newEmail = employee.getEmail();
            if (employeeService.getEmployeeById(newId) != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else if (employeeService.findByEmail(newEmail) != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                Employee newEmployee = employeeService.addEmployee(employee);
                return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee){
        try{
            if (employeeService.getEmployeeById(id) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            employeeService.updateEmployee(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        try{
            if (employeeService.getEmployeeById(id) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                employeeService.deleteEmployee(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}