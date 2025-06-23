package com.example.shopberry.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequestDto {

    @Schema(
            description = "Current password of the user",
            example = "OldP@ssw0rd123"
    )
    @NotBlank
    @JsonProperty("current_password")
    private String currentPassword;

    @Schema(
            description = "New password the user wants to set",
            example = "NewP@ssw0rd456"
    )
    @NotBlank
    @JsonProperty("new_password")
    private String newPassword;

    @Schema(
            description = "Confirmation of the new password, must match exactly with newPassword field",
            example = "NewP@ssw0rd456"
    )
    @NotBlank
    @JsonProperty("confirmation_password")
    private String confirmationPassword;

}
