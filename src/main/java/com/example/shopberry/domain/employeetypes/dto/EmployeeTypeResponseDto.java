package com.example.shopberry.domain.employeetypes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTypeResponseDto {

    @JsonProperty("employee_type_id")
    private Long employeeTypeId;

    @JsonProperty("employee_type_name")
    private String employeeTypeName;

}
