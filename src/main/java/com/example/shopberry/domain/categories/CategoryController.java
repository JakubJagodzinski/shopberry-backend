package com.example.shopberry.domain.categories;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.categories.dto.CategoryResponseDto;
import com.example.shopberry.domain.categories.dto.CreateCategoryRequestDto;
import com.example.shopberry.domain.categories.dto.UpdateCategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getCategories() {
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.getCategories();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryResponseDtoList);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long categoryId) {
        CategoryResponseDto categoryResponseDto = categoryService.getCategoryById(categoryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryResponseDto);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CreateCategoryRequestDto createCategoryRequestDto) {
        CategoryResponseDto createdCategory = categoryService.createCategory(createCategoryRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/categories/" + createdCategory.getCategoryId()))
                .body(createdCategory);
    }

    @PostMapping("/{childCategoryId}/set-parent-category/{parentCategoryId}")
    public ResponseEntity<CategoryResponseDto> setParentCategory(@PathVariable Long childCategoryId, @PathVariable Long parentCategoryId) {
        CategoryResponseDto updatedCategoryResponseDto = categoryService.setParentCategory(childCategoryId, parentCategoryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCategoryResponseDto);
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long categoryId, @RequestBody UpdateCategoryRequestDto updateCategoryRequestDto) {
        CategoryResponseDto updatedCategoryResponseDto = categoryService.updateCategoryById(categoryId, updateCategoryRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCategoryResponseDto);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<MessageResponseDto> deleteCategoryById(@PathVariable Long categoryId) {
        categoryService.deleteCategoryById(categoryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Category with id " + categoryId + " deleted successfully"));
    }

}
