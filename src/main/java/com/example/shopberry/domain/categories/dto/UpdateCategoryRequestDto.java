package com.example.shopberry.domain.categories.dto;

import com.example.shopberry.common.constants.messages.CategoryMessages;
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
            nullable = true,
            minLength = 1
    )
    @NotEmptyIfPresent(
            message = CategoryMessages.CATEGORY_NAME_CANNOT_BE_EMPTY,
            notBlankMessage = CategoryMessages.CATEGORY_NAME_CANNOT_CONTAIN_ONLY_NAMESPACES
    )
    @JsonProperty("category_name")
    private String categoryName;

}
