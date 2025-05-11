package com.example.internet_shop.employees.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRequestDto {

    private Long employeeTypeId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
