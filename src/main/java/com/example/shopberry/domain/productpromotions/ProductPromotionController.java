package com.example.shopberry.domain.productpromotions;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.productpromotions.dto.AssignPromotionToProductRequestDto;
import com.example.shopberry.domain.productpromotions.dto.ProductPromotionResponseDto;
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
public class ProductPromotionController {

    private final ProductPromotionService productPromotionService;

    @GetMapping("/product-promotions")
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
    public ResponseEntity<ProductPromotionResponseDto> assignPromotionToProduct(@PathVariable Long productId, @RequestBody AssignPromotionToProductRequestDto assignPromotionToProductRequestDto) {
        ProductPromotionResponseDto createdProductPromotionResponseDto = productPromotionService.assignPromotionToProduct(productId, assignPromotionToProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/products/" + productId + "/promotions/" + createdProductPromotionResponseDto.getPromotion()))
                .body(createdProductPromotionResponseDto);
    }

    @CheckPermission(Permission.PRODUCT_PROMOTION_UNASSIGN)
    @DeleteMapping("/products/{productId}/promotions")
    public ResponseEntity<MessageResponseDto> unassignAllPromotionsFromProduct(@PathVariable Long productId) {
        productPromotionService.unassignAllPromotionsFromProduct(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("All promotions unassigned from product with id " + productId + " successfully"));
    }

    @CheckPermission(Permission.PRODUCT_PROMOTION_UNASSIGN_ALL)
    @DeleteMapping("/products/promotions/{promotionId}")
    public ResponseEntity<MessageResponseDto> unassignPromotionFromAllProducts(@PathVariable Long promotionId) {
        productPromotionService.deleteProductPromotionsByPromotionId(promotionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Promotion with id " + promotionId + " unassigned from all products successfully"));
    }

}
