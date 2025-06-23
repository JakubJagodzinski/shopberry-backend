package com.example.shopberry.domain.categories;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.categories.dto.request.CreateCategoryRequestDto;
import com.example.shopberry.domain.categories.dto.request.SetParentCategoryRequestDto;
import com.example.shopberry.domain.categories.dto.request.UpdateCategoryRequestDto;
import com.example.shopberry.domain.categories.dto.response.CategoryResponseDto;
import com.example.shopberry.domain.categories.dto.response.CategoryTreeResponseDto;
import com.example.shopberry.exception.ApiError;
import com.example.shopberry.user.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get all categories, flat, not nested")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of categories",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponseDto.class)
                    )
            )
    })
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.getAllCategories();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryResponseDtoList);
    }

    @Operation(summary = "Get categories tree")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categories tree",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryTreeResponseDto.class)
                    )
            )
    })
    @GetMapping("/categories/tree")
    public ResponseEntity<List<CategoryTreeResponseDto>> getCategoriesTree() {
        List<CategoryTreeResponseDto> categoryTreeResponseDtoList = categoryService.getCategoriesTree();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryTreeResponseDtoList);
    }

    @Operation(summary = "Get category by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Category found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long categoryId) {
        CategoryResponseDto categoryResponseDto = categoryService.getCategoryById(categoryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryResponseDto);
    }

    @Operation(summary = "Create new category")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Category created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Category with that name already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.CATEGORY_CREATE)
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CreateCategoryRequestDto createCategoryRequestDto) {
        CategoryResponseDto createdCategory = categoryService.createCategory(createCategoryRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/categories/" + createdCategory.getCategoryId()))
                .body(createdCategory);
    }

    @Operation(summary = "Set parent category")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Parent category set",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Children or parent category not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.CATEGORY_UPDATE)
    @PostMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryResponseDto> setParentCategory(@PathVariable Long categoryId, @Valid @RequestBody SetParentCategoryRequestDto setParentCategoryRequestDto) {
        CategoryResponseDto updatedCategoryResponseDto = categoryService.setParentCategory(categoryId, setParentCategoryRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCategoryResponseDto);
    }

    @Operation(summary = "Update category by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Category updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Category with that name already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.CATEGORY_UPDATE)
    @PatchMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryResponseDto> updateCategoryById(@PathVariable Long categoryId, @Valid @RequestBody UpdateCategoryRequestDto updateCategoryRequestDto) {
        CategoryResponseDto updatedCategoryResponseDto = categoryService.updateCategoryById(categoryId, updateCategoryRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCategoryResponseDto);
    }

    @Operation(summary = "Delete category by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Category deleted"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.CATEGORY_DELETE)
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long categoryId) {
        categoryService.deleteCategoryById(categoryId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
