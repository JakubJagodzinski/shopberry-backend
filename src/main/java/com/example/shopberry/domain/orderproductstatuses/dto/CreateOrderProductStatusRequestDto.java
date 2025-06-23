package com.example.shopberry.domain.orderproductstatuses.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CreateOrderProductStatusRequestDto {

    @Schema(
            description = "Unique name of the order product status",
            example = "Pending"
    )
    @NotBlank
    @JsonProperty("status_name")
    private String statusName;

    @Schema(
            description = "Detailed description of status",
            example = "Waiting for futher actions...",
            minLength = 1,
            maxLength = 500
    )
    @NotBlank
    private String description;

}
