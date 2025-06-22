package com.example.shopberry.domain.employees.dto;

import com.example.shopberry.domain.employees.Employee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeDtoMapper {

    public EmployeeResponseDto toDto(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();

        employeeResponseDto.setEmployeeId(employee.getUserId());
        employeeResponseDto.setFirstName(employee.getFirstName());
        employeeResponseDto.setLastName(employee.getLastName());
        employeeResponseDto.setCreatedAt(employee.getCreatedAt());
        employeeResponseDto.setHiredAt(employee.getHiredAt());
        employeeResponseDto.setIsActive(employee.getIsActive());

        return employeeResponseDto;
    }

    public List<EmployeeResponseDto> toDtoList(List<Employee> employeeList) {
        return employeeList.stream()
                .map(this::toDto)
                .toList();
    }

}
