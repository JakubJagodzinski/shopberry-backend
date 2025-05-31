package com.example.shopberry.domain.productpromotions;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.productpromotions.dto.CreateProductPromotionRequestDto;
import com.example.shopberry.domain.productpromotions.dto.ProductPromotionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product-promotions")
@RequiredArgsConstructor
public class ProductPromotionController {

    private final ProductPromotionService productPromotionService;

    @GetMapping("/")
    public ResponseEntity<List<ProductPromotionResponseDto>> getProductPromotions() {
        List<ProductPromotionResponseDto> productPromotionResponseDtoList = productPromotionService.getProductPromotions();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productPromotionResponseDtoList);
    }

    @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<ProductPromotionResponseDto>> getProductPromotionsByProductId(@PathVariable Long productId) {
        List<ProductPromotionResponseDto> productPromotionResponseDtoList = productPromotionService.getProductPromotionsByProductId(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productPromotionResponseDtoList);
    }

    @GetMapping("/by-promotion/{promotionId}")
    public ResponseEntity<List<ProductPromotionResponseDto>> getProductPromotionsByPromotionId(@PathVariable Long promotionId) {
        List<ProductPromotionResponseDto> productPromotionResponseDtoList = productPromotionService.getProductPromotionsByPromotionId(promotionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productPromotionResponseDtoList);
    }

    @PostMapping("/")
    public ResponseEntity<ProductPromotionResponseDto> createProductPromotion(@RequestBody CreateProductPromotionRequestDto createProductPromotionRequestDto) {
        ProductPromotionResponseDto createdProductPromotionResponseDto = productPromotionService.createProductPromotion(createProductPromotionRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/product-promotions/" + createdProductPromotionResponseDto.getProductId() + "/" + createdProductPromotionResponseDto.getPromotionId()))
                .body(createdProductPromotionResponseDto);
    }

    @DeleteMapping("/by-product/{productId}")
    public ResponseEntity<MessageResponseDto> deleteProductPromotionsByProductId(@PathVariable Long productId) {
        productPromotionService.deleteProductPromotionsByProductId(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Product promotions with productId " + productId + " deleted successfully"));
    }

    @DeleteMapping("/by-promotion/{promotionId}")
    public ResponseEntity<MessageResponseDto> deleteProductPromotionsByPromotionId(@PathVariable Long promotionId) {
        productPromotionService.deleteProductPromotionsByPromotionId(promotionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Product promotions with promotionId " + promotionId + " deleted successfully"));
    }

}
