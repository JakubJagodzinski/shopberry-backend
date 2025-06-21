package com.example.shopberry.domain.employees;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.employees.dto.EmployeeResponseDto;
import com.example.shopberry.domain.employees.dto.UpdateEmployeeRequestDto;
import com.example.shopberry.user.Permission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    @CheckPermission(Permission.EMPLOYEE_READ_ALL)
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        List<EmployeeResponseDto> employeeResponseDtoList = employeeService.getAllEmployees();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResponseDtoList);
    }

    @CheckPermission(Permission.EMPLOYEE_READ)
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable UUID employeeId) {
        EmployeeResponseDto employeeResponseDto = employeeService.getEmployeeById(employeeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResponseDto);
    }

    @CheckPermission(Permission.EMPLOYEE_UPDATE)
    @PatchMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeResponseDto> updateEmployeeById(@PathVariable UUID employeeId, @Valid @RequestBody UpdateEmployeeRequestDto updateEmployeeRequestDto) {
        EmployeeResponseDto updatedEmployeeResponseDto = employeeService.updateEmployeeById(employeeId, updateEmployeeRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedEmployeeResponseDto);
    }

    @CheckPermission(Permission.EMPLOYEE_DELETE)
    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<MessageResponseDto> deleteEmployeeById(@PathVariable UUID employeeId) {
        employeeService.deleteEmployeeById(employeeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Employee with id " + employeeId + " deleted successfully"));
    }

}
