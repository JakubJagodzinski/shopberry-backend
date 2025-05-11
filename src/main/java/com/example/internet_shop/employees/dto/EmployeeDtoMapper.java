package com.example.internet_shop.employees.dto;

import com.example.internet_shop.employees.Employee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeDtoMapper {

    public EmployeeResponseDto toDto(Employee employee) {
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();

        employeeResponseDto.setEmployeeId(employee.getEmployeeId());
        employeeResponseDto.setEmployeeTypeId(employee.getEmployeeType().getEmployeeTypeId());
        employeeResponseDto.setFirstName(employee.getFirstName());
        employeeResponseDto.setLastName(employee.getLastName());
        employeeResponseDto.setEmail(employee.getEmail());
        employeeResponseDto.setEmployedAt(employee.getEmployedAt());

        return employeeResponseDto;
    }

    public List<EmployeeResponseDto> toDtoList(List<Employee> employees) {
        return employees.stream()
                .map(this::toDto)
                .toList();
    }

}
