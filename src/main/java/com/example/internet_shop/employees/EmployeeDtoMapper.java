package com.example.internet_shop.employees;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeDtoMapper {

    public EmployeeDto toDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setEmployeeId(employee.getEmployeeId());
        employeeDto.setEmployeeTypeId(employee.getEmployeeType().getEmployeeTypeId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setEmployedAt(employee.getEmployedAt());

        return employeeDto;
    }

    public List<EmployeeDto> toDtoList(List<Employee> employees) {
        return employees.stream()
                .map(this::toDto)
                .toList();
    }

}
