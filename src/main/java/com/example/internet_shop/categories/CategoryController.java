package com.example.internet_shop.categories;

import com.example.internet_shop.categories.dto.CategoryResponseDto;
import com.example.internet_shop.categories.dto.CreateCategoryRequestDto;
import com.example.internet_shop.categories.dto.UpdateCategoryRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryResponseDto>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping("/")
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CreateCategoryRequestDto createCategoryRequestDto) {
        CategoryResponseDto createdCategory = categoryService.createCategory(createCategoryRequestDto);
        return ResponseEntity.created(URI.create("/api/categories/" + createdCategory.getCategoryId())).body(createdCategory);
    }

    @PostMapping("/{childId}/set_parent/{parentId}")
    public ResponseEntity<CategoryResponseDto> setParentCategory(@PathVariable Long childId, @PathVariable Long parentId) {
        return ResponseEntity.ok(categoryService.setParentCategory(childId, parentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequestDto updateCategoryRequestDto) {
        return ResponseEntity.ok(categoryService.updateCategoryById(id, updateCategoryRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Deleted category with id " + id);
    }

}
