package com.example.internet_shop.employeetypes;

import com.example.internet_shop.employeetypes.dto.CreateEmployeeTypeRequestDto;
import com.example.internet_shop.employeetypes.dto.EmployeeTypeResponseDto;
import com.example.internet_shop.employeetypes.dto.UpdateEmployeeTypeRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employee-types")
public class EmployeeTypeController {

    private final EmployeeTypeService employeeTypeService;

    public EmployeeTypeController(EmployeeTypeService employeeTypeService) {
        this.employeeTypeService = employeeTypeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeTypeResponseDto>> getEmployeeTypes() {
        return ResponseEntity.ok(employeeTypeService.getEmployeeTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeTypeResponseDto> getEmployeeTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeTypeService.getEmployeeTypeById(id));
    }

    @PostMapping("/")
    public ResponseEntity<EmployeeTypeResponseDto> createEmployeeType(@RequestBody CreateEmployeeTypeRequestDto createEmployeeTypeRequestDto) {
        EmployeeTypeResponseDto createdEmployeeTypeResponseDto = employeeTypeService.createEmployeeType(createEmployeeTypeRequestDto);
        return ResponseEntity.created(URI.create("/api/v1/employee-types/" + createdEmployeeTypeResponseDto.getEmployeeTypeId())).body(createdEmployeeTypeResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeTypeResponseDto> updateEmployeeTypeById(@PathVariable Long id, @RequestBody UpdateEmployeeTypeRequestDto updateEmployeeTypeRequestDto) {
        return ResponseEntity.ok(employeeTypeService.updateEmployeeTypeById(id, updateEmployeeTypeRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeTypeById(@PathVariable Long id) {
        employeeTypeService.deleteEmployeeTypeById(id);

        return ResponseEntity.ok("Deleted employee type with id " + id);
    }

}
