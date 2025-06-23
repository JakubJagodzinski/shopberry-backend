package com.example.shopberry.domain.productpromotions;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.productpromotions.dto.request.AssignPromotionToProductRequestDto;
import com.example.shopberry.domain.productpromotions.dto.response.ProductPromotionResponseDto;
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
public class ProductPromotionController {

    private final ProductPromotionService productPromotionService;

    @Operation(summary = "Get all product-promotion entries")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of product-promotion entries",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductPromotionResponseDto.class)
                    )
            )
    })
    @GetMapping("/products/promotions")
    public ResponseEntity<List<ProductPromotionResponseDto>> getAllProductPromotions() {
        List<ProductPromotionResponseDto> productPromotionResponseDtoList = productPromotionService.getAllProductPromotions();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productPromotionResponseDtoList);
    }

    @Operation(summary = "Get all product-promotion entries by promotion id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of product-promotion entries by promotion id",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductPromotionResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Promotion not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @GetMapping("/products/promotions/{promotionId}")
    public ResponseEntity<List<ProductPromotionResponseDto>> getAllProductsWithPromotion(@PathVariable Long promotionId) {
        List<ProductPromotionResponseDto> productPromotionResponseDtoList = productPromotionService.getAllProductsWithPromotion(promotionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productPromotionResponseDtoList);
    }

    @Operation(summary = "Get product's all promotions")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of product's promotions",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductPromotionResponseDto.class)
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
    @GetMapping("/products/{productId}/promotions")
    public ResponseEntity<List<ProductPromotionResponseDto>> getProductAllPromotions(@PathVariable Long productId) {
        List<ProductPromotionResponseDto> productPromotionResponseDtoList = productPromotionService.getProductAllPromotions(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productPromotionResponseDtoList);
    }

    @Operation(summary = "Assign promotion to product")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Promotion assigned to product successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductPromotionResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Promotion already assigned to product",
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
                    description = "Product / promotion not found not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PRODUCT_PROMOTION_ASSIGN)
    @PostMapping("/products/{productId}/promotions")
    public ResponseEntity<ProductPromotionResponseDto> assignPromotionToProduct(@PathVariable Long productId, @Valid @RequestBody AssignPromotionToProductRequestDto assignPromotionToProductRequestDto) {
        ProductPromotionResponseDto createdProductPromotionResponseDto = productPromotionService.assignPromotionToProduct(productId, assignPromotionToProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/products/" + productId + "/promotions/" + createdProductPromotionResponseDto.getPromotion().getPromotionId()))
                .body(createdProductPromotionResponseDto);
    }

    @Operation(summary = "Unassign promotion from product")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Promotion unassigned from product successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Promotion not assigned to product",
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
                    description = "Product / promotion not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PRODUCT_PROMOTION_UNASSIGN)
    @DeleteMapping("/products/{productId}/promotions")
    public ResponseEntity<Void> unassignAllPromotionsFromProduct(@PathVariable Long productId) {
        productPromotionService.unassignAllPromotionsFromProduct(productId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Unassign all promotions from product")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "All promotions from product unassigned from product successfully"
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
                    description = "Promotion not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PRODUCT_PROMOTION_UNASSIGN_ALL)
    @DeleteMapping("/products/promotions/{promotionId}")
    public ResponseEntity<Void> unassignPromotionFromAllProducts(@PathVariable Long promotionId) {
        productPromotionService.deleteProductPromotionsByPromotionId(promotionId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
