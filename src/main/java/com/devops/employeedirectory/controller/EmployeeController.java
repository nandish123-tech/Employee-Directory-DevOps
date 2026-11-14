package com.devops.employeedirectory.controller;

import com.devops.employeedirectory.model.Employee;
import com.devops.employeedirectory.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Value("${app.name:employee-directory}")
    private String appName;

    @Value("${app.version:1.0.0}")
    private String appVersion;

    @Value("${app.message:Welcome to Employee Directory REST API}")
    private String appMessage;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Root info endpoint (reports host/pod name, useful for load balancing demonstration)
    @GetMapping("/")
    public Map<String, String> getAppInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("app", appName);
        info.put("version", appVersion);
        info.put("message", appMessage);
        try {
            info.put("host", InetAddress.getLocalHost().getHostName());
        } catch (Exception e) {
            info.put("host", "unknown");
        }
        return info;
    }

    // 1. GET /employees - Retrieve all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // 2. GET /employees/{id} - Retrieve an employee by ID
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    // 3. POST /employees - Add a new employee
    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee createdEmployee = employeeService.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }
}
