package com.example.shopberry.domain.orderproducts;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.orderproducts.dto.AddProductToOrderRequestDto;
import com.example.shopberry.domain.orderproducts.dto.OrderProductResponseDto;
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
public class OrderProductController {

    private final OrderProductService orderProductService;

    @CheckPermission(Permission.ORDER_PRODUCT_READ)
    @GetMapping("/orders/{orderId}/products/{productId}")
    public ResponseEntity<OrderProductResponseDto> getOrderProductById(@PathVariable Long orderId, @PathVariable Long productId) {
        OrderProductResponseDto orderProductResponseDto = orderProductService.getOrderProductById(orderId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductResponseDto);
    }

    @CheckPermission(Permission.ORDER_PRODUCT_READ_ALL)
    @GetMapping("/orders/{orderId}/products")
    public ResponseEntity<List<OrderProductResponseDto>> getOrderAllProducts(@PathVariable Long orderId) {
        List<OrderProductResponseDto> orderProductResponseDtoList = orderProductService.getOrderAllProducts(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductResponseDtoList);
    }

    @CheckPermission(Permission.ORDER_PRODUCT_ADD)
    @PostMapping("/orders/{orderId}/products")
    public ResponseEntity<OrderProductResponseDto> addProductToOrder(@PathVariable Long orderId, @Valid @RequestBody AddProductToOrderRequestDto addProductToOrderRequestDto) {
        OrderProductResponseDto createdOrderProductResponseDto = orderProductService.addProductToOrder(orderId, addProductToOrderRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/orders/" + orderId + "/products/" + createdOrderProductResponseDto.getProduct()))
                .body(createdOrderProductResponseDto);
    }

    @CheckPermission(Permission.ORDER_PRODUCT_REMOVE)
    @DeleteMapping("/orders/{orderId}/products/{productId}")
    public ResponseEntity<MessageResponseDto> removeProductFromOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        orderProductService.removeProductFromOrder(orderId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Product with id " + productId + " removed from order with id " + orderId));
    }

}
