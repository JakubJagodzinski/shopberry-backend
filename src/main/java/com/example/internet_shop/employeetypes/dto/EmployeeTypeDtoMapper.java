package com.example.internet_shop.employeetypes.dto;

import com.example.internet_shop.employeetypes.EmployeeType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeTypeDtoMapper {

    public EmployeeTypeResponseDto toDto(EmployeeType employeeType) {
        EmployeeTypeResponseDto EmployeeTypeResponseDto = new EmployeeTypeResponseDto();

        EmployeeTypeResponseDto.setEmployeeTypeId(employeeType.getEmployeeTypeId());
        EmployeeTypeResponseDto.setEmployeeTypeName(employeeType.getEmployeeTypeName());

        return EmployeeTypeResponseDto;
    }

    public List<EmployeeTypeResponseDto> toDtoList(List<EmployeeType> employeeTypes) {
        return employeeTypes.stream()
                .map(this::toDto)
                .toList();
    }

}
