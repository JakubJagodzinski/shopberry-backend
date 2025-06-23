package com.example.shopberry.domain.causesofreturn.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCauseOfReturnRequestDto {

    @Schema(
            description = "Cause of return unique name",
            example = "Product broken"
    )
    @NotBlank
    private String cause;

}
