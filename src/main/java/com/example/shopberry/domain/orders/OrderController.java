package com.example.shopberry.domain.orders;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.orders.dto.CreateOrderRequestDto;
import com.example.shopberry.domain.orders.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getOrders() {
        List<OrderResponseDto> orderResponseDtoList = orderService.getOrders();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderResponseDtoList);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId) {
        OrderResponseDto orderResponseDto = orderService.getOrderById(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderResponseDto);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto createOrderRequestDto) {
        OrderResponseDto createdOrderResponseDto = orderService.createOrder(createOrderRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/orders/" + createdOrderResponseDto.getOrderId()))
                .body(createdOrderResponseDto);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<MessageResponseDto> deleteOrderById(@PathVariable Long orderId) {
        orderService.deleteOrderById(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Order with id " + orderId + " deleted successfully"));
    }

}
