package com.example.internet_shop.employeetypes;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeTypeDtoMapper {

    public EmployeeTypeDto toDto(EmployeeType employeeType) {
        EmployeeTypeDto EmployeeTypeDto = new EmployeeTypeDto();

        EmployeeTypeDto.setEmployeeTypeId(employeeType.getEmployeeTypeId());
        EmployeeTypeDto.setEmployeeTypeName(employeeType.getEmployeeTypeName());

        return EmployeeTypeDto;
    }

    public List<EmployeeTypeDto> toDtoList(List<EmployeeType> employeeTypes) {
        return employeeTypes.stream()
                .map(this::toDto)
                .toList();
    }

}
