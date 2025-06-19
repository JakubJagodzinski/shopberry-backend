package com.example.shopberry.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RegisterRequestDto {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    private String password;

    @JsonProperty("is_company")
    private Boolean isCompany = false;

    @JsonProperty("employee_type_id")
    private Long employeeTypeId;

    private String role;

}
