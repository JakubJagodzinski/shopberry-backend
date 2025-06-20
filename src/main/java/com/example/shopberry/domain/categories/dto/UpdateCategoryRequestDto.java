package com.example.shopberry.domain.categories.dto;

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
public class UpdateCategoryRequestDto {

    @Schema(
            description = "Name of the category",
            example = "Electronics",
            nullable = true,
            minLength = 1
    )
    @NotEmptyIfPresent
    @JsonProperty("category_name")
    private String categoryName;

}
