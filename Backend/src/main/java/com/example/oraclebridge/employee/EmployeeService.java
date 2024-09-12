package com.example.oraclebridge.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
    public Employee getEmployeeById(Long id){
        return employeeRepository.findById(id).orElse(null);
    }
    public Employee addEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }
    public Employee updateEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> findByFirstName(String FIRST_NAME){
        return employeeRepository.findByFirstName(FIRST_NAME);
    }
    public Employee findByEmail(String email){
        return employeeRepository.findEmployeesByEmail(email);
    }

}
