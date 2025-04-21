package com.example.internet_shop.categories;

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
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CreateCategoryDto createCategoryDto) {
        CategoryDto createdCategory = categoryService.createCategory(createCategoryDto);
        return ResponseEntity.created(URI.create("/api/categories/" + createdCategory.getCategoryId())).body(createdCategory);
    }

    @PostMapping("/{childId}/set_parent/{parentId}")
    public ResponseEntity<CategoryDto> setParentCategory(@PathVariable Long childId, @PathVariable Long parentId) {
        return ResponseEntity.ok(categoryService.setParentCategory(childId, parentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryDto updateCategoryDto) {
        return ResponseEntity.ok(categoryService.updateCategoryById(id, updateCategoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Deleted category with id " + id);
    }

}
