package com.example.shopberry.domain.products;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.products.dto.CreateProductRequestDto;
import com.example.shopberry.domain.products.dto.ProductResponseDto;
import com.example.shopberry.domain.products.dto.UpdateProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        List<ProductResponseDto> productResponseDtoList = productService.getProducts();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDtoList);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId) {
        ProductResponseDto productResponseDto = productService.getProductById(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDto);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        ProductResponseDto createdProductResponseDto = productService.createProduct(createProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/products/" + createdProductResponseDto.getProductId()))
                .body(createdProductResponseDto);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> updateProductById(@PathVariable Long productId, @RequestBody UpdateProductRequestDto updateProductRequestDto) {
        ProductResponseDto updatedProductResponseDto = productService.updateProductById(productId, updateProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedProductResponseDto);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<MessageResponseDto> deleteProductById(@PathVariable Long productId) {
        productService.deleteProductById(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Product with id " + productId + " deleted successfully"));
    }

}
