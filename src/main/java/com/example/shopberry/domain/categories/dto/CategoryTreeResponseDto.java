package com.example.shopberry.domain.categories.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryTreeResponseDto {

    private Long categoryId;

    private String categoryName;

    private List<CategoryTreeResponseDto> children = new ArrayList<>();

}
