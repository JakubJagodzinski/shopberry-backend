package com.example.internet_shop.employeetypes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employee_types")
public class EmployeeTypeController {

    private final EmployeeTypeService employeeTypeService;

    public EmployeeTypeController(EmployeeTypeService employeeTypeService) {
        this.employeeTypeService = employeeTypeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeTypeDto>> getEmployeeTypes() {
        return ResponseEntity.ok(employeeTypeService.getEmployeeTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeTypeDto> getEmployeeTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeTypeService.getEmployeeTypeById(id));
    }

    @PostMapping("/")
    public ResponseEntity<EmployeeTypeDto> createEmployeeType(@RequestBody CreateEmployeeTypeDto createEmployeeTypeDto) {
        EmployeeTypeDto createdEmployeeTypeDto = employeeTypeService.createEmployeeType(createEmployeeTypeDto);
        return ResponseEntity.created(URI.create("/api/employee_types/" + createdEmployeeTypeDto.getEmployeeTypeId())).body(createdEmployeeTypeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeTypeDto> updateEmployeeTypeById(@PathVariable Long id, @RequestBody UpdateEmployeeTypeDto updateEmployeeTypeDto) {
        return ResponseEntity.ok(employeeTypeService.updateEmployeeTypeById(id, updateEmployeeTypeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeTypeById(@PathVariable Long id) {
        employeeTypeService.deleteEmployeeTypeById(id);

        return ResponseEntity.ok("Deleted employee type with id " + id);
    }

}
