package com.example.shopberry.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RegisterRequestDto {

    @Schema(
            description = "User's first name",
            example = "John"
    )
    @NotBlank
    @JsonProperty("first_name")
    private String firstName;

    @Schema(
            description = "User's last name",
            example = "Doe"
    )
    @NotBlank
    @JsonProperty("last_name")
    private String lastName;

    @Schema(
            description = "User's email address",
            example = "john.doe@example.com"
    )
    @Email(message = "Invalid email format")
    @NotBlank
    private String email;

    @Schema(
            description = "User's password",
            example = "P@ssw0rd123"
    )
    @NotBlank
    private String password;

    @Schema(
            description = "User's role in the system",
            examples = {
                    "CUSTOMER",
                    "EMPLOYEE"
            }
    )
    @NotBlank
    private String role;

    @Schema(
            description = "Indicates if the user registers as a company",
            example = "true",
            defaultValue = "false",
            nullable = true
    )
    @JsonProperty("is_company")
    private Boolean isCompany = false;

}
