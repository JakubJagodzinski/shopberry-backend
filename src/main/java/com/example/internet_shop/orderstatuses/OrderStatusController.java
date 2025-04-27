package com.example.internet_shop.orderstatuses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/order_statuses")
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderStatusDto>> getOrderStatuses() {
        return ResponseEntity.ok(orderStatusService.getOrderStatuses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderStatusDto> getOrderStatusById(@PathVariable Long id) {
        return ResponseEntity.ok(orderStatusService.getOrderStatusById(id));
    }

    @PostMapping("/")
    public ResponseEntity<OrderStatusDto> createOrderStatus(@RequestBody CreateOrderStatusDto createOrderStatusDto) {
        OrderStatusDto createdOrderStatus = orderStatusService.createOrderStatus(createOrderStatusDto);

        return ResponseEntity.created(URI.create("/api/order_statuses/" + createdOrderStatus.getOrderStatusId())).body(createdOrderStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderStatusDto> updateOrderStatusById(@PathVariable Long id, @RequestBody UpdateOrderStatusDto updateOrderStatusDto) {
        return ResponseEntity.ok(orderStatusService.updateOrderStatusById(id, updateOrderStatusDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderStatusById(@PathVariable Long id) {
        orderStatusService.deleteOrderStatusById(id);

        return ResponseEntity.ok("Deleted order status with id: " + id);
    }

}
