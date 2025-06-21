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
            description = "Unique name of the attribute",
            example = "Color",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    @JsonProperty("attribute_name")
    private String attributeName;

}
