package com.example.shopberry.domain.categories.dto.request;

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
public class CreateCategoryRequestDto {

    @Schema(
            description = "Unique name of the category",
            example = "Electronics"
    )
    @NotBlank
    @JsonProperty("category_name")
    private String categoryName;

    @Schema(
            description = "Id of another category to be set as a parent for this one",
            example = "1",
            defaultValue = "null",
            nullable = true
    )
    @JsonProperty("parent_category_id")
    private Long parentCategoryId;

}
