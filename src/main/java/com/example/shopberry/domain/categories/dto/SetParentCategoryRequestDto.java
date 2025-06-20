package com.example.shopberry.domain.categories.dto;

import com.example.shopberry.common.constants.messages.CategoryMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SetParentCategoryRequestDto {

    @Schema(
            description = "Id of another category to be set as a parent. Category cannot be parent to itself",
            example = "1"
    )
    @NotNull(message = CategoryMessages.CATEGORY_ID_CANNOT_BE_NULL)
    @JsonProperty("parent_category_id")
    private Long parentCategoryId;

}
