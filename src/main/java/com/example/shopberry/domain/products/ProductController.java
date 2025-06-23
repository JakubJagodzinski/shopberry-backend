package com.example.shopberry.domain.products;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.productattributes.dto.response.ProductWithAttributesResponseDto;
import com.example.shopberry.domain.products.dto.request.CreateProductRequestDto;
import com.example.shopberry.domain.products.dto.request.UpdateProductRequestDto;
import com.example.shopberry.domain.products.dto.response.ProductResponseDto;
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
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductWithAttributesResponseDto>> getAllProductsWithParams(@RequestParam(required = false) Long categoryId, @RequestParam(required = false) String productName) {
        List<ProductWithAttributesResponseDto> productResponseDtoList = productService.getAllProductsWithParams(categoryId, productName);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDtoList);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId) {
        ProductResponseDto productResponseDto = productService.getProductById(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDto);
    }

    @CheckPermission(Permission.PRODUCT_CREATE)
    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody CreateProductRequestDto createProductRequestDto) {
        ProductResponseDto createdProductResponseDto = productService.createProduct(createProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/products/" + createdProductResponseDto.getProductId()))
                .body(createdProductResponseDto);
    }

    @CheckPermission(Permission.PRODUCT_UPDATE)
    @PatchMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDto> updateProductById(@PathVariable Long productId, @Valid @RequestBody UpdateProductRequestDto updateProductRequestDto) {
        ProductResponseDto updatedProductResponseDto = productService.updateProductById(productId, updateProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedProductResponseDto);
    }

    @CheckPermission(Permission.PRODUCT_DELETE)
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {
        productService.deleteProductById(productId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
