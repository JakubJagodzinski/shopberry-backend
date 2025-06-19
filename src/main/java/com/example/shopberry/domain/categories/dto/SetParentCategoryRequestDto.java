package com.example.shopberry.domain.categories.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SetParentCategoryRequestDto {

    @JsonProperty("parent_category_id")
    private Long parentCategoryId;

}
