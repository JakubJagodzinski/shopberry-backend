package com.example.shopberry.categoriesattributes;

import com.example.shopberry.categoriesattributes.dto.CategoryAttributeResponseDto;
import com.example.shopberry.categoriesattributes.dto.CreateCategoryAttributeRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category-attributes")
public class CategoryAttributeController {

    private final CategoryAttributeService categoryAttributeService;

    public CategoryAttributeController(CategoryAttributeService categoryAttributeService) {
        this.categoryAttributeService = categoryAttributeService;
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<CategoryAttributeResponseDto>> getCategoryAttributesByCategoryId(@PathVariable Long categoryId) {
        List<CategoryAttributeResponseDto> categoryAttributeResponseDtoList = categoryAttributeService.getCategoryAttributesByCategoryId(categoryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryAttributeResponseDtoList);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryAttributeResponseDto> createCategoryAttribute(@RequestBody CreateCategoryAttributeRequestDto createCategoryAttributeRequestDto) {
        CategoryAttributeResponseDto createdCategoryAttributeResponseDto = categoryAttributeService.createCategoryAttribute(createCategoryAttributeRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/category-attributes/" + createdCategoryAttributeResponseDto.getCategoryId()))
                .body(createdCategoryAttributeResponseDto);
    }

    @DeleteMapping("/{categoryId}/{attributeId}")
    public ResponseEntity<String> deleteCategoryAttributeById(@PathVariable Long categoryId, @PathVariable Long attributeId) {
        CategoryAttributeId categoryAttributeId = new CategoryAttributeId(categoryId, attributeId);

        categoryAttributeService.deleteCategoryAttributeById(categoryAttributeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Category attribute with id " + categoryAttributeId + " deleted successfully");
    }

}
