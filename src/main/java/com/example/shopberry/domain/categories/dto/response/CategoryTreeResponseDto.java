package com.example.shopberry.domain.categories.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"category_id", "category_name", "children"})
public class CategoryTreeResponseDto {

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("category_name")
    private String categoryName;

    private List<CategoryTreeResponseDto> children = new ArrayList<>();

}
