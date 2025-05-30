package com.example.shopberry.domain.employees.dto;

import com.example.shopberry.domain.employees.Employee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeDtoMapper {

    public EmployeeResponseDto toDto(Employee employee) {
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();

        employeeResponseDto.setEmployeeId(employee.getId());
        employeeResponseDto.setEmployeeTypeId(employee.getEmployeeType().getEmployeeTypeId());
        employeeResponseDto.setFirstName(employee.getFirstName());
        employeeResponseDto.setLastName(employee.getLastName());
        employeeResponseDto.setEmployedAt(employee.getCreatedAt());

        return employeeResponseDto;
    }

    public List<EmployeeResponseDto> toDtoList(List<Employee> employees) {
        return employees.stream()
                .map(this::toDto)
                .toList();
    }

}
