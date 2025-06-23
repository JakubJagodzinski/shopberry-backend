package com.example.shopberry.domain.categoriesattributes;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.attributes.dto.response.AttributeResponseDto;
import com.example.shopberry.domain.categoriesattributes.dto.request.AssignAttributeToCategoryRequestDto;
import com.example.shopberry.domain.categoriesattributes.dto.response.CategoryAttributeResponseDto;
import com.example.shopberry.user.Permission;
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

    @GetMapping("/categories/{categoryId}/attributes")
    public ResponseEntity<List<AttributeResponseDto>> getCategoryAllAttributes(@PathVariable Long categoryId) {
        List<AttributeResponseDto> categoryAttributeResponseDtoList = categoryAttributeService.getCategoryAllAttributes(categoryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryAttributeResponseDtoList);
    }

    @CheckPermission(Permission.CATEGORY_ATTRIBUTE_ASSIGN)
    @PostMapping("/categories/{categoryId}/attributes")
    public ResponseEntity<CategoryAttributeResponseDto> assignAttributeToCategory(@PathVariable Long categoryId, @Valid @RequestBody AssignAttributeToCategoryRequestDto assignAttributeToCategoryRequestDto) {
        CategoryAttributeResponseDto createdCategoryAttributeResponseDto = categoryAttributeService.assignAttributeToCategory(categoryId, assignAttributeToCategoryRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/categories/" + categoryId + "/attributes/" + createdCategoryAttributeResponseDto.getAttribute().getAttributeId()))
                .body(createdCategoryAttributeResponseDto);
    }

    @CheckPermission(Permission.CATEGORY_ATTRIBUTE_UNASSIGN)
    @DeleteMapping("/categories/{categoryId}/attributes/{attributeId}")
    public ResponseEntity<MessageResponseDto> unassignAttributeFromCategory(@PathVariable Long categoryId, @PathVariable Long attributeId) {
        categoryAttributeService.unassignAttributeFromCategory(categoryId, attributeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Attribute with id " + attributeId + " removed from category with id " + categoryId));
    }

}
