package com.example.shopberry.domain.productreturns;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.productreturns.dto.request.CreateProductReturnRequestDto;
import com.example.shopberry.domain.productreturns.dto.response.ProductReturnResponseDto;
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
public class ProductReturnController {

    private final ProductReturnService productReturnService;

    @CheckPermission(Permission.PRODUCT_RETURN_READ)
    @GetMapping("/product-returns/{productReturnId}")
    public ResponseEntity<ProductReturnResponseDto> getProductReturnById(@PathVariable Long productReturnId) {
        ProductReturnResponseDto productReturnResponseDto = productReturnService.getProductReturnById(productReturnId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productReturnResponseDto);
    }

    @CheckPermission(Permission.ORDER_PRODUCT_RETURN_READ_ALL)
    @GetMapping("/orders/{orderId}/product-returns")
    public ResponseEntity<List<ProductReturnResponseDto>> getOrderAllProductReturns(@PathVariable Long orderId) {
        List<ProductReturnResponseDto> productReturnResponseDtoList = productReturnService.getOrderAllProductReturns(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productReturnResponseDtoList);
    }

    @CheckPermission(Permission.PRODUCT_RETURN_CREATE)
    @PostMapping("/orders/{orderId}/product-returns")
    public ResponseEntity<ProductReturnResponseDto> createProductReturn(@PathVariable Long orderId, @Valid @RequestBody CreateProductReturnRequestDto createProductReturnRequestDto) {
        ProductReturnResponseDto createdProductReturnResponseDto = productReturnService.createProductReturn(orderId, createProductReturnRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/orders/" + orderId + "/product-returns/" + createdProductReturnResponseDto.getProductReturnId()))
                .body(createdProductReturnResponseDto);
    }

    @CheckPermission(Permission.PRODUCT_RETURN_DELETE)
    @DeleteMapping("/product-returns/{productReturnId}")
    public ResponseEntity<MessageResponseDto> deleteProductReturnById(@PathVariable Long productReturnId) {
        productReturnService.deleteProductReturnById(productReturnId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Product return with id " + productReturnId + " deleted successfully"));
    }

}
