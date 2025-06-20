package com.example.shopberry.domain.employeetypes.dto;

import com.example.shopberry.domain.employeetypes.EmployeeType;
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

    public List<EmployeeTypeResponseDto> toDtoList(List<EmployeeType> employeeTypeList) {
        return employeeTypeList.stream()
                .map(this::toDto)
                .toList();
    }

}
