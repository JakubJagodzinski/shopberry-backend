package com.example.internet_shop.products;

import com.example.internet_shop.products.dto.CreateProductRequestDto;
import com.example.internet_shop.products.dto.ProductResponseDto;
import com.example.internet_shop.products.dto.UpdateProductRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        List<ProductResponseDto> productResponseDtoList = productService.getProducts();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        ProductResponseDto productResponseDto = productService.getProductById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        ProductResponseDto createdProductResponseDto = productService.createProduct(createProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/products/" + createdProductResponseDto.getProductId()))
                .body(createdProductResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProductById(@PathVariable Long id, @RequestBody UpdateProductRequestDto updateProductRequestDto) {
        ProductResponseDto updatedProductResponseDto = productService.updateProductById(id, updateProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedProductResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Product with id " + id + " deleted successfully");
    }

}
