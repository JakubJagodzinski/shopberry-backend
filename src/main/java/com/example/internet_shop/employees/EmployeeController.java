package com.example.internet_shop.employees;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        return ResponseEntity.ok(employeeService.getEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping("/")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody CreateEmployeeDto createEmployeeDto) {
        EmployeeDto createdEmployee = employeeService.createEmployee(createEmployeeDto);

        return ResponseEntity.created(URI.create("/api/employees/" + createdEmployee.getEmployeeId())).body(createdEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);

        return ResponseEntity.ok("Deleted employee with id " + id);
    }

}
