package com.example.shopberry.domain.orderproducts;

import com.example.shopberry.domain.orderproducts.dto.CreateOrderProductRequestDto;
import com.example.shopberry.domain.orderproducts.dto.OrderProductResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order-products")
public class OrderProductController {

    private final OrderProductService orderProductService;

    public OrderProductController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    @GetMapping("/{orderId}/{productId}")
    public ResponseEntity<OrderProductResponseDto> getOrderProductById(@PathVariable Long orderId, @PathVariable Long productId) {
        OrderProductId orderProductId = new OrderProductId(orderId, productId);

        OrderProductResponseDto orderProductResponseDto = orderProductService.getOrderProductById(orderProductId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductResponseDto);
    }

    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<List<OrderProductResponseDto>> getOrderProductsByOrderId(@PathVariable Long orderId) {
        List<OrderProductResponseDto> orderProductResponseDtoList = orderProductService.getOrderProductsByOrderId(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductResponseDtoList);
    }

    @PostMapping("/")
    public ResponseEntity<OrderProductResponseDto> createOrderProduct(@RequestBody CreateOrderProductRequestDto createOrderProductRequestDto) {
        OrderProductResponseDto createdOrderProductResponseDto = orderProductService.createOrderProduct(createOrderProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/order-products/" + createdOrderProductResponseDto.getOrderId() + "/" + createdOrderProductResponseDto.getProductId()))
                .body(createdOrderProductResponseDto);
    }

    @DeleteMapping("/{orderId}/{productId}")
    public ResponseEntity<String> deleteOrderProductById(@PathVariable Long orderId, @PathVariable Long productId) {
        OrderProductId orderProductId = new OrderProductId(orderId, productId);

        orderProductService.deleteOrderProductById(orderProductId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Order product with id " + orderProductId + " deleted successfully");
    }

}
