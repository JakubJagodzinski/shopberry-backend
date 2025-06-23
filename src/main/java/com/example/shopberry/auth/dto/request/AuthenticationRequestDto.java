package com.example.shopberry.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AuthenticationRequestDto {

    @Schema(
            description = "User's email address used for login",
            example = "user@example.com"
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

}
