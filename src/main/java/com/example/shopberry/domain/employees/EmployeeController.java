package com.example.shopberry.domain.employees;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.employees.dto.request.UpdateEmployeeRequestDto;
import com.example.shopberry.domain.employees.dto.response.EmployeeResponseDto;
import com.example.shopberry.exception.ApiError;
import com.example.shopberry.user.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all employees")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of employees",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.EMPLOYEE_READ_ALL)
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        List<EmployeeResponseDto> employeeResponseDtoList = employeeService.getAllEmployees();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResponseDtoList);
    }

    @Operation(summary = "Get employee by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employee not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.EMPLOYEE_READ)
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable UUID employeeId) {
        EmployeeResponseDto employeeResponseDto = employeeService.getEmployeeById(employeeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeResponseDto);
    }

    @Operation(summary = "Update employee by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employee not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.EMPLOYEE_UPDATE)
    @PatchMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeResponseDto> updateEmployeeById(@PathVariable UUID employeeId, @Valid @RequestBody UpdateEmployeeRequestDto updateEmployeeRequestDto) {
        EmployeeResponseDto updatedEmployeeResponseDto = employeeService.updateEmployeeById(employeeId, updateEmployeeRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedEmployeeResponseDto);
    }

    @Operation(summary = "Delete employee by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Employee deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employee not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.EMPLOYEE_DELETE)
    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable UUID employeeId) {
        employeeService.deleteEmployeeById(employeeId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
