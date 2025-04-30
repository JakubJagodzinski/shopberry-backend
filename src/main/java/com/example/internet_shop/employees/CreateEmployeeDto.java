package com.example.internet_shop.employees;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeDto {

    private Long employeeTypeId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
