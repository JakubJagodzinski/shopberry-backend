package com.example.shopberry.domain.employees;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.employees.dto.EmployeeResponseDto;
import com.example.shopberry.domain.employees.dto.UpdateEmployeeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getEmployees() {
        List<EmployeeResponseDto> employeeResponseDtoList = employeeService.getEmployees();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResponseDtoList);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long employeeId) {
        EmployeeResponseDto employeeResponseDto = employeeService.getEmployeeById(employeeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResponseDto);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDto> updateEmployeeById(@PathVariable Long employeeId, @RequestBody UpdateEmployeeRequestDto updateEmployeeRequestDto) {
        EmployeeResponseDto updatedEmployeeResponseDto = employeeService.updateEmployeeById(employeeId, updateEmployeeRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedEmployeeResponseDto);
    }


    @DeleteMapping("/{employeeId}")
    public ResponseEntity<MessageResponseDto> deleteEmployeeById(@PathVariable Long employeeId) {
        employeeService.deleteEmployeeById(employeeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Employee with id " + employeeId + " deleted successfully"));
    }

}
