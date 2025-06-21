package com.example.shopberry.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RefreshTokenRequestDto {

    @Schema(
            description = "Refresh token used to obtain a new access token",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    @NotBlank
    @JsonProperty("refresh_token")
    private String refreshToken;

}
