package com.example.shopberry.orders;

import com.example.shopberry.orders.dto.CreateOrderRequestDto;
import com.example.shopberry.orders.dto.OrderResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderResponseDto>> getOrders() {
        List<OrderResponseDto> orderResponseDtoList = orderService.getOrders();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        OrderResponseDto orderResponseDto = orderService.getOrderById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto createOrderRequestDto) {
        OrderResponseDto createdOrderResponseDto = orderService.createOrder(createOrderRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/orders/" + createdOrderResponseDto.getOrderId()))
                .body(createdOrderResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Order with id " + id + " deleted successfully");
    }

}
