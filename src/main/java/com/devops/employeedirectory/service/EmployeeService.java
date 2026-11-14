package com.devops.employeedirectory.service;

import com.devops.employeedirectory.exception.EmployeeNotFoundException;
import com.devops.employeedirectory.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeService {

    // Thread-safe in-memory store
    private final List<Employee> employeeRepository = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public EmployeeService() {
        // Pre-populate with initial sample records
        employeeRepository.add(new Employee(idGenerator.getAndIncrement(), "Alice Smith", "Engineering", "alice.smith@example.com"));
        employeeRepository.add(new Employee(idGenerator.getAndIncrement(), "Bob Jones", "Quality Assurance", "bob.jones@example.com"));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository;
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employee addEmployee(Employee employee) {
        Employee newEmployee = new Employee(
                idGenerator.getAndIncrement(),
                employee.getName(),
                employee.getDepartment(),
                employee.getEmail()
        );
        employeeRepository.add(newEmployee);
        return newEmployee;
    }
}
