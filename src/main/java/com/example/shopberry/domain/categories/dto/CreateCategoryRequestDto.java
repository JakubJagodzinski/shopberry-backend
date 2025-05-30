package com.example.shopberry.domain.categories.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequestDto {

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("parent_category_id")
    private Long parentCategoryId;

}
