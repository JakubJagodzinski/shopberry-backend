package com.example.shopberry.domain.employeetypes;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.employeetypes.dto.CreateEmployeeTypeRequestDto;
import com.example.shopberry.domain.employeetypes.dto.EmployeeTypeResponseDto;
import com.example.shopberry.domain.employeetypes.dto.UpdateEmployeeTypeRequestDto;
import com.example.shopberry.user.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmployeeTypeController {

    private final EmployeeTypeService employeeTypeService;

    @CheckPermission(Permission.EMPLOYEE_TYPE_READ_ALL)
    @GetMapping("/employee-types")
    public ResponseEntity<List<EmployeeTypeResponseDto>> getAllEmployeeTypes() {
        List<EmployeeTypeResponseDto> employeeTypeResponseDtoList = employeeTypeService.getAllEmployeeTypes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeTypeResponseDtoList);
    }

    @CheckPermission(Permission.EMPLOYEE_TYPE_READ)
    @GetMapping("/employee-types/{employeeTypeId}")
    public ResponseEntity<EmployeeTypeResponseDto> getEmployeeTypeById(@PathVariable Long employeeTypeId) {
        EmployeeTypeResponseDto employeeTypeResponseDto = employeeTypeService.getEmployeeTypeById(employeeTypeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeTypeResponseDto);
    }

    @CheckPermission(Permission.EMPLOYEE_TYPE_CREATE)
    @PostMapping("/employee-types")
    public ResponseEntity<EmployeeTypeResponseDto> createEmployeeType(@RequestBody CreateEmployeeTypeRequestDto createEmployeeTypeRequestDto) {
        EmployeeTypeResponseDto createdEmployeeTypeResponseDto = employeeTypeService.createEmployeeType(createEmployeeTypeRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/employee-types/" + createdEmployeeTypeResponseDto.getEmployeeTypeId()))
                .body(createdEmployeeTypeResponseDto);
    }

    @CheckPermission(Permission.EMPLOYEE_TYPE_UPDATE)
    @PatchMapping("/employee-types/{employeeTypeId}")
    public ResponseEntity<EmployeeTypeResponseDto> updateEmployeeTypeById(@PathVariable Long employeeTypeId, @RequestBody UpdateEmployeeTypeRequestDto updateEmployeeTypeRequestDto) {
        EmployeeTypeResponseDto updatedEmployeeTypeResponseDto = employeeTypeService.updateEmployeeTypeById(employeeTypeId, updateEmployeeTypeRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedEmployeeTypeResponseDto);
    }

    @CheckPermission(Permission.EMPLOYEE_TYPE_DELETE)
    @DeleteMapping("/employee-types/{employeeTypeId}")
    public ResponseEntity<MessageResponseDto> deleteEmployeeTypeById(@PathVariable Long employeeTypeId) {
        employeeTypeService.deleteEmployeeTypeById(employeeTypeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Employee type with id " + employeeTypeId + " deleted successfully"));
    }

}
