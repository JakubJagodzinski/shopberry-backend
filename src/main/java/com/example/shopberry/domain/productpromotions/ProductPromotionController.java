package com.example.shopberry.domain.productpromotions;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.productpromotions.dto.request.AssignPromotionToProductRequestDto;
import com.example.shopberry.domain.productpromotions.dto.response.ProductPromotionResponseDto;
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
public class ProductPromotionController {

    private final ProductPromotionService productPromotionService;

    @GetMapping("/products/promotions")
    public ResponseEntity<List<ProductPromotionResponseDto>> getAllProductPromotions() {
        List<ProductPromotionResponseDto> productPromotionResponseDtoList = productPromotionService.getAllProductPromotions();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productPromotionResponseDtoList);
    }

    @GetMapping("/products/promotions/{promotionId}")
    public ResponseEntity<List<ProductPromotionResponseDto>> getAllProductsWithPromotion(@PathVariable Long promotionId) {
        List<ProductPromotionResponseDto> productPromotionResponseDtoList = productPromotionService.getAllProductsWithPromotion(promotionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productPromotionResponseDtoList);
    }

    @GetMapping("/products/{productId}/promotions")
    public ResponseEntity<List<ProductPromotionResponseDto>> getProductAllPromotions(@PathVariable Long productId) {
        List<ProductPromotionResponseDto> productPromotionResponseDtoList = productPromotionService.getProductAllPromotions(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productPromotionResponseDtoList);
    }

    @CheckPermission(Permission.PRODUCT_PROMOTION_ASSIGN)
    @PostMapping("/products/{productId}/promotions")
    public ResponseEntity<ProductPromotionResponseDto> assignPromotionToProduct(@PathVariable Long productId, @Valid @RequestBody AssignPromotionToProductRequestDto assignPromotionToProductRequestDto) {
        ProductPromotionResponseDto createdProductPromotionResponseDto = productPromotionService.assignPromotionToProduct(productId, assignPromotionToProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/products/" + productId + "/promotions/" + createdProductPromotionResponseDto.getPromotion().getPromotionId()))
                .body(createdProductPromotionResponseDto);
    }

    @CheckPermission(Permission.PRODUCT_PROMOTION_UNASSIGN)
    @DeleteMapping("/products/{productId}/promotions")
    public ResponseEntity<Void> unassignAllPromotionsFromProduct(@PathVariable Long productId) {
        productPromotionService.unassignAllPromotionsFromProduct(productId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @CheckPermission(Permission.PRODUCT_PROMOTION_UNASSIGN_ALL)
    @DeleteMapping("/products/promotions/{promotionId}")
    public ResponseEntity<Void> unassignPromotionFromAllProducts(@PathVariable Long promotionId) {
        productPromotionService.deleteProductPromotionsByPromotionId(promotionId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
