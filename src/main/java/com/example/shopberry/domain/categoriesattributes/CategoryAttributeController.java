package com.example.shopberry.domain.categoriesattributes;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.attributes.dto.response.AttributeResponseDto;
import com.example.shopberry.domain.categoriesattributes.dto.request.AssignAttributeToCategoryRequestDto;
import com.example.shopberry.domain.categoriesattributes.dto.response.CategoryAttributeResponseDto;
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
public class CategoryAttributeController {

    private final CategoryAttributeService categoryAttributeService;

    @Operation(summary = "Get category all attributes")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of category attributes",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AttributeResponseDto.class)
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
    @GetMapping("/categories/{categoryId}/attributes")
    public ResponseEntity<List<AttributeResponseDto>> getCategoryAllAttributes(@PathVariable Long categoryId) {
        List<AttributeResponseDto> categoryAttributeResponseDtoList = categoryAttributeService.getCategoryAllAttributes(categoryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryAttributeResponseDtoList);
    }

    @Operation(summary = "Get category with all its attributes")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "List of category with all its attributes",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryAttributeResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Attribute already assigned to category",
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
    @CheckPermission(Permission.CATEGORY_ATTRIBUTE_ASSIGN)
    @PostMapping("/categories/{categoryId}/attributes")
    public ResponseEntity<CategoryAttributeResponseDto> assignAttributeToCategory(@PathVariable Long categoryId, @Valid @RequestBody AssignAttributeToCategoryRequestDto assignAttributeToCategoryRequestDto) {
        CategoryAttributeResponseDto createdCategoryAttributeResponseDto = categoryAttributeService.assignAttributeToCategory(categoryId, assignAttributeToCategoryRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/categories/" + categoryId + "/attributes/" + createdCategoryAttributeResponseDto.getAttribute().getAttributeId()))
                .body(createdCategoryAttributeResponseDto);
    }

    @Operation(summary = "Unassign attribute from category")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Attribute unassigned from category successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Attribute not assigned to category",
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
                    description = "Category / attribute not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.CATEGORY_ATTRIBUTE_UNASSIGN)
    @DeleteMapping("/categories/{categoryId}/attributes/{attributeId}")
    public ResponseEntity<Void> unassignAttributeFromCategory(@PathVariable Long categoryId, @PathVariable Long attributeId) {
        categoryAttributeService.unassignAttributeFromCategory(categoryId, attributeId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
