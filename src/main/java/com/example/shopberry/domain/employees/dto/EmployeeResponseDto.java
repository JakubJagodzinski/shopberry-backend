package com.example.shopberry.domain.employees.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto {

    private Long employeeId;

    private Long employeeTypeId;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDateTime employedAt;

}
