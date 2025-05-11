package com.example.internet_shop.employees;

import com.example.internet_shop.employees.dto.CreateEmployeeRequestDto;
import com.example.internet_shop.employees.dto.EmployeeResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployees() {
        return ResponseEntity.ok(employeeService.getEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping("/")
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody CreateEmployeeRequestDto createEmployeeRequestDto) {
        EmployeeResponseDto createdEmployee = employeeService.createEmployee(createEmployeeRequestDto);

        return ResponseEntity.created(URI.create("/api/v1/employees/" + createdEmployee.getEmployeeId())).body(createdEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);

        return ResponseEntity.ok("Deleted employee with id " + id);
    }

}
