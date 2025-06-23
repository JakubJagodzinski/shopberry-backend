package com.example.shopberry.domain.productattributes;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.productattributes.dto.request.AssignAttributeToProductRequestDto;
import com.example.shopberry.domain.productattributes.dto.request.UpdateProductAttributeRequestDto;
import com.example.shopberry.domain.productattributes.dto.response.ProductAttributeResponseDto;
import com.example.shopberry.domain.productattributes.dto.response.ProductWithAttributesResponseDto;
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
public class ProductAttributeController {

    private final ProductAttributeService productAttributeService;

    @Operation(summary = "Get product all attributes")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product with attributes found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductWithAttributesResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @GetMapping("/products/{productId}/attributes")
    public ResponseEntity<ProductWithAttributesResponseDto> getProductAllAttributes(@PathVariable Long productId) {
        ProductWithAttributesResponseDto productAttributeResponseDtoList = productAttributeService.getProductAllAttributes(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productAttributeResponseDtoList);
    }

    @Operation(summary = "Get all product-attribute list")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of products with its attributes",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductAttributeResponseDto.class)
                    )
            )
    })
    @GetMapping("/products/attributes/{attributeId}")
    public ResponseEntity<List<ProductAttributeResponseDto>> getAllProductAttributeList(@PathVariable Long attributeId) {
        List<ProductAttributeResponseDto> productAttributeResponseDtoList = productAttributeService.getAllProductAttributeList(attributeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productAttributeResponseDtoList);
    }

    @Operation(summary = "Assign attribute to product")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Attribute assigned to product successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductAttributeResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Attribute already assigned to product",
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
                    description = "Product / attribute not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PRODUCT_ATTRIBUTE_ASSIGN)
    @PostMapping("/products/{productId}/attributes")
    public ResponseEntity<ProductAttributeResponseDto> assignAttributeToProduct(@PathVariable Long productId, @Valid @RequestBody AssignAttributeToProductRequestDto assignAttributeToProductRequestDto) {
        ProductAttributeResponseDto createdProductAttributeResponseDto = productAttributeService.assignAttributeToProduct(productId, assignAttributeToProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/products/" + productId + "/attributes/" + createdProductAttributeResponseDto.getAttribute().getAttributeId()))
                .body(createdProductAttributeResponseDto);
    }

    @Operation(summary = "Update product attribute by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product with updated attribute",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductAttributeResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Attribute not assigned to product",
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
                    description = "Product / attribute not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PRODUCT_ATTRIBUTE_UPDATE)
    @PatchMapping("/products/{productId}/attributes/{attributeId}")
    public ResponseEntity<ProductAttributeResponseDto> updateProductAttributeById(@PathVariable Long productId, @PathVariable Long attributeId, @Valid @RequestBody UpdateProductAttributeRequestDto updateProductAttributeRequestDto) {
        ProductAttributeResponseDto updatedProductAttributeResponseDto = productAttributeService.updateProductAttributeById(productId, attributeId, updateProductAttributeRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedProductAttributeResponseDto);
    }

    @Operation(summary = "Unassign attribute from product")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Attribute unassigned from product successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Attribute not assigned to product",
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
                    description = "Product / attribute not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PRODUCT_ATTRIBUTE_UNASSIGN)
    @DeleteMapping("/products/{productId}/attributes/{attributeId}")
    public ResponseEntity<Void> unassignAttributeFromProduct(@PathVariable Long productId, @PathVariable Long attributeId) {
        productAttributeService.unassignAttributeFromProduct(productId, attributeId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
