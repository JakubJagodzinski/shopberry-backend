package com.example.internet_shop.employees;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private Long employeeId;

    private Long employeeTypeId;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDateTime employedAt;

}
