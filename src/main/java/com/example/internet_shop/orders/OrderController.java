package com.example.internet_shop.orders;

import com.example.internet_shop.orders.dto.CreateOrderRequestDto;
import com.example.internet_shop.orders.dto.OrderResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderResponseDto>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping("/")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto createOrderRequestDto) {
        OrderResponseDto createdOrder = orderService.createOrder(createOrderRequestDto);

        return ResponseEntity.created(URI.create("/api/orders/" + createdOrder.getOrderId())).body(createdOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);

        return ResponseEntity.ok("Deleted order with id: " + id);
    }

}
