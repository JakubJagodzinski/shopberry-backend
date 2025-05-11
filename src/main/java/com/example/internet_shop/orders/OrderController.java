package com.example.internet_shop.orders;

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
    public ResponseEntity<List<OrderDto>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping("/")
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderDto createOrderDto) {
        OrderDto createdOrder = orderService.createOrder(createOrderDto);

        return ResponseEntity.created(URI.create("/api/orders/" + createdOrder.getOrderId())).body(createdOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);

        return ResponseEntity.ok("Deleted order with id: " + id);
    }

}
