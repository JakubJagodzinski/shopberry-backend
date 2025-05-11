package com.example.internet_shop.employees;

import com.example.internet_shop.employees.dto.CreateEmployeeRequestDto;
import com.example.internet_shop.employees.dto.EmployeeResponseDto;
import org.springframework.http.HttpStatus;
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
        List<EmployeeResponseDto> employeeResponseDtoList = employeeService.getEmployees();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) {
        EmployeeResponseDto employeeResponseDto = employeeService.getEmployeeById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody CreateEmployeeRequestDto createEmployeeRequestDto) {
        EmployeeResponseDto createdEmployeeResponseDto = employeeService.createEmployee(createEmployeeRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/employees/" + createdEmployeeResponseDto.getEmployeeId()))
                .body(createdEmployeeResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Employee with id " + id + " deleted successfully");
    }

}
