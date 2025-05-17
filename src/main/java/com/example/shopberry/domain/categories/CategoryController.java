package com.example.shopberry.domain.categories;

import com.example.shopberry.domain.categories.dto.CategoryResponseDto;
import com.example.shopberry.domain.categories.dto.CreateCategoryRequestDto;
import com.example.shopberry.domain.categories.dto.UpdateCategoryRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryResponseDto>> getCategories() {
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.getCategories();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        CategoryResponseDto categoryResponseDto = categoryService.getCategoryById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CreateCategoryRequestDto createCategoryRequestDto) {
        CategoryResponseDto createdCategory = categoryService.createCategory(createCategoryRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/categories/" + createdCategory.getCategoryId()))
                .body(createdCategory);
    }

    @PostMapping("/{childId}/set_parent/{parentId}")
    public ResponseEntity<CategoryResponseDto> setParentCategory(@PathVariable Long childId, @PathVariable Long parentId) {
        CategoryResponseDto updatedCategoryResponseDto = categoryService.setParentCategory(childId, parentId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCategoryResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequestDto updateCategoryRequestDto) {
        CategoryResponseDto updatedCategoryResponseDto = categoryService.updateCategoryById(id, updateCategoryRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCategoryResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Category with id " + id + " deleted successfully");
    }

}
