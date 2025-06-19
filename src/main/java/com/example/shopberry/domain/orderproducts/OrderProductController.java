package com.example.shopberry.domain.orderproducts;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.orderproducts.dto.AddProductToOrderRequestDto;
import com.example.shopberry.domain.orderproducts.dto.OrderProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderProductController {

    private final OrderProductService orderProductService;

    @GetMapping("/orders/{orderId}/products/{productId}")
    public ResponseEntity<OrderProductResponseDto> getOrderProductById(@PathVariable Long orderId, @PathVariable Long productId) {
        OrderProductResponseDto orderProductResponseDto = orderProductService.getOrderProductById(orderId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductResponseDto);
    }

    @GetMapping("/orders/{orderId}/products")
    public ResponseEntity<List<OrderProductResponseDto>> getOrderAllProducts(@PathVariable Long orderId) {
        List<OrderProductResponseDto> orderProductResponseDtoList = orderProductService.getOrderAllProducts(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductResponseDtoList);
    }

    @PostMapping("/orders/{orderId}/products")
    public ResponseEntity<OrderProductResponseDto> addProductToOrder(@PathVariable Long orderId, @RequestBody AddProductToOrderRequestDto addProductToOrderRequestDto) {
        OrderProductResponseDto createdOrderProductResponseDto = orderProductService.addProductToOrder(orderId, addProductToOrderRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/orders/" + orderId + "/products/" + createdOrderProductResponseDto.getProductId()))
                .body(createdOrderProductResponseDto);
    }

    @DeleteMapping("/orders/{orderId}/products/{productId}")
    public ResponseEntity<MessageResponseDto> removeProductFromOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        orderProductService.removeProductFromOrder(orderId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Product with id " + productId + " removed from order with id " + orderId));
    }

}
