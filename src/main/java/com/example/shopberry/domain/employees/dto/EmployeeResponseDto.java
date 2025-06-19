package com.example.shopberry.domain.employees.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto {

    @JsonProperty("employee_id")
    private UUID employeeId;

    @JsonProperty("employee_type_id")
    private Long employeeTypeId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    @JsonProperty("employed_at")
    private LocalDateTime employedAt;

}
