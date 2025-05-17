package com.example.shopberry.employeetypes;

import com.example.shopberry.employeetypes.dto.CreateEmployeeTypeRequestDto;
import com.example.shopberry.employeetypes.dto.EmployeeTypeResponseDto;
import com.example.shopberry.employeetypes.dto.UpdateEmployeeTypeRequestDto;
import org.springframework.http.HttpStatus;
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
        List<EmployeeTypeResponseDto> employeeTypeResponseDtoList = employeeTypeService.getEmployeeTypes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeTypeResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeTypeResponseDto> getEmployeeTypeById(@PathVariable Long id) {
        EmployeeTypeResponseDto employeeTypeResponseDto = employeeTypeService.getEmployeeTypeById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeTypeResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<EmployeeTypeResponseDto> createEmployeeType(@RequestBody CreateEmployeeTypeRequestDto createEmployeeTypeRequestDto) {
        EmployeeTypeResponseDto createdEmployeeTypeResponseDto = employeeTypeService.createEmployeeType(createEmployeeTypeRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/employee-types/" + createdEmployeeTypeResponseDto.getEmployeeTypeId()))
                .body(createdEmployeeTypeResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeTypeResponseDto> updateEmployeeTypeById(@PathVariable Long id, @RequestBody UpdateEmployeeTypeRequestDto updateEmployeeTypeRequestDto) {
        EmployeeTypeResponseDto updatedEmployeeTypeResponseDto = employeeTypeService.updateEmployeeTypeById(id, updateEmployeeTypeRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedEmployeeTypeResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeTypeById(@PathVariable Long id) {
        employeeTypeService.deleteEmployeeTypeById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Employee type with id " + id + " deleted successfully");
    }

}
