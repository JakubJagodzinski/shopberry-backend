package com.example.internet_shop.products;

import com.example.internet_shop.products.dto.CreateProductRequestDto;
import com.example.internet_shop.products.dto.ProductResponseDto;
import com.example.internet_shop.products.dto.UpdateProductRequestDto;
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
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        ProductResponseDto createdProduct = productService.createProduct(createProductRequestDto);
        return ResponseEntity.created(URI.create("/api/v1/products/" + createdProduct.getProductId())).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProductById(@PathVariable Long id, @RequestBody UpdateProductRequestDto updateProductRequestDto) {
        return ResponseEntity.ok(productService.updateProductById(id, updateProductRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Deleted product with id " + id);
    }

}
