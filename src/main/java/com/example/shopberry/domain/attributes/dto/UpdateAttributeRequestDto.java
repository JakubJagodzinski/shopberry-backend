package com.example.shopberry.domain.attributes.dto;

import com.example.shopberry.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAttributeRequestDto {

    @Schema(
            description = "Name of the attribute",
            example = "Color",
            nullable = true,
            minLength = 1
    )
    @NotEmptyIfPresent
    @JsonProperty("attribute_name")
    private String attributeName;

}
