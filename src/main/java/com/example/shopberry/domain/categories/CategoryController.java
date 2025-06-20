package com.example.shopberry.domain.categories;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.categories.dto.CategoryResponseDto;
import com.example.shopberry.domain.categories.dto.CreateCategoryRequestDto;
import com.example.shopberry.domain.categories.dto.SetParentCategoryRequestDto;
import com.example.shopberry.domain.categories.dto.UpdateCategoryRequestDto;
import com.example.shopberry.user.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.getAllCategories();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryResponseDtoList);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long categoryId) {
        CategoryResponseDto categoryResponseDto = categoryService.getCategoryById(categoryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryResponseDto);
    }

    @CheckPermission(Permission.CATEGORY_CREATE)
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CreateCategoryRequestDto createCategoryRequestDto) {
        CategoryResponseDto createdCategory = categoryService.createCategory(createCategoryRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/categories/" + createdCategory.getCategoryId()))
                .body(createdCategory);
    }

    @CheckPermission(Permission.CATEGORY_UPDATE)
    @PostMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryResponseDto> setParentCategory(@PathVariable Long categoryId, @RequestBody SetParentCategoryRequestDto setParentCategoryRequestDto) {
        CategoryResponseDto updatedCategoryResponseDto = categoryService.setParentCategory(categoryId, setParentCategoryRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCategoryResponseDto);
    }

    @CheckPermission(Permission.CATEGORY_UPDATE)
    @PatchMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryResponseDto> updateCategoryById(@PathVariable Long categoryId, @RequestBody UpdateCategoryRequestDto updateCategoryRequestDto) {
        CategoryResponseDto updatedCategoryResponseDto = categoryService.updateCategoryById(categoryId, updateCategoryRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCategoryResponseDto);
    }

    @CheckPermission(Permission.CATEGORY_DELETE)
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<MessageResponseDto> deleteCategoryById(@PathVariable Long categoryId) {
        categoryService.deleteCategoryById(categoryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Category with id " + categoryId + " deleted successfully"));
    }

}
