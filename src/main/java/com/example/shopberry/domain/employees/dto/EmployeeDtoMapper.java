package com.example.shopberry.domain.employees.dto;

import com.example.shopberry.domain.employees.Employee;
import com.example.shopberry.domain.employeetypes.dto.EmployeeTypeDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeDtoMapper {

    private final EmployeeTypeDtoMapper employeeTypeDtoMapper;

    public EmployeeResponseDto toDto(Employee employee) {
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();

        employeeResponseDto.setEmployeeId(employee.getUserId());
        employeeResponseDto.setEmployeeType(employeeTypeDtoMapper.toDto(employee.getEmployeeType()));
        employeeResponseDto.setFirstName(employee.getFirstName());
        employeeResponseDto.setLastName(employee.getLastName());
        employeeResponseDto.setEmployedAt(employee.getCreatedAt());

        return employeeResponseDto;
    }

    public List<EmployeeResponseDto> toDtoList(List<Employee> employeeList) {
        return employeeList.stream()
                .map(this::toDto)
                .toList();
    }

}
