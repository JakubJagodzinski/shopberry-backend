package com.example.shopberry.domain.categories.dto.request;

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
            description = "Unique name of the category",
            example = "Electronics",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    @JsonProperty("category_name")
    private String categoryName;

}
