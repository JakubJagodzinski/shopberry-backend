package com.example.shopberry.domain.attributes.dto;

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
public class CreateAttributeRequestDto {

    @Schema(
            description = "Unique name of the attribute",
            example = "Color"
    )
    @NotBlank
    @JsonProperty("attribute_name")
    private String attributeName;

}
