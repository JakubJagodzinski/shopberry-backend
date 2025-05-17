package com.example.shopberry.productreturns;

import com.example.shopberry.productreturns.dto.CreateProductReturnRequestDto;
import com.example.shopberry.productreturns.dto.ProductReturnResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product-returns")
public class ProductReturnController {

    private final ProductReturnService productReturnService;

    public ProductReturnController(ProductReturnService productReturnService) {
        this.productReturnService = productReturnService;
    }

    @GetMapping("/{productReturnId}")
    public ResponseEntity<ProductReturnResponseDto> getProductReturnById(@PathVariable Long productReturnId) {
        ProductReturnResponseDto productReturnResponseDto = productReturnService.getProductReturnById(productReturnId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productReturnResponseDto);
    }

    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<List<ProductReturnResponseDto>> getProductReturnsByOrderId(@PathVariable Long orderId) {
        List<ProductReturnResponseDto> productReturnResponseDtoList = productReturnService.getProductReturnsByOrderId(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productReturnResponseDtoList);
    }

    @PostMapping("/")
    public ResponseEntity<ProductReturnResponseDto> createProductReturn(@RequestBody CreateProductReturnRequestDto createProductReturnRequestDto) {
        ProductReturnResponseDto createdProductReturnResponseDto = productReturnService.createProductReturn(createProductReturnRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/product-returns/" + createdProductReturnResponseDto.getProductReturnId()))
                .body(createdProductReturnResponseDto);
    }

    @DeleteMapping("/{productReturnId}")
    public ResponseEntity<String> deleteProductReturnById(@PathVariable Long productReturnId) {
        productReturnService.deleteProductReturnById(productReturnId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Product return with id " + productReturnId + " deleted successfully");
    }

}
