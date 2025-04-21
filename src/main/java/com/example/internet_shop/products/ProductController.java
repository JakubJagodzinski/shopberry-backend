package com.example.internet_shop.products;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@RequestBody CreateProductDto createProductDto) {
        ProductDto createdProduct = productService.createProduct(createProductDto);
        return ResponseEntity.created(URI.create("/api/producers/" + createdProduct.getProductId())).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> editProductById(@PathVariable Long id, @RequestBody EditProductDto editProductDto) {
        return ResponseEntity.ok(productService.editProductById(id, editProductDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Deleted product with id " + id);
    }

}
