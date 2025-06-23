package com.example.shopberry.domain.employees.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"employee_id", "first_name", "last_name", "created_at", "hired_at", "is_active"})
public class EmployeeResponseDto {

    @JsonProperty("employee_id")
    private UUID employeeId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("hired_at")
    private LocalDate hiredAt;

    @JsonProperty("is_active")
    private Boolean isActive;

}
