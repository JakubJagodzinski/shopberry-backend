package com.example.internet_shop.orderstatuses;

import com.example.internet_shop.orderstatuses.dto.CreateOrderStatusRequestDto;
import com.example.internet_shop.orderstatuses.dto.OrderStatusResponseDto;
import com.example.internet_shop.orderstatuses.dto.UpdateOrderStatusRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order-statuses")
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderStatusResponseDto>> getOrderStatuses() {
        return ResponseEntity.ok(orderStatusService.getOrderStatuses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderStatusResponseDto> getOrderStatusById(@PathVariable Long id) {
        return ResponseEntity.ok(orderStatusService.getOrderStatusById(id));
    }

    @PostMapping("/")
    public ResponseEntity<OrderStatusResponseDto> createOrderStatus(@RequestBody CreateOrderStatusRequestDto createOrderStatusRequestDto) {
        OrderStatusResponseDto createdOrderStatus = orderStatusService.createOrderStatus(createOrderStatusRequestDto);

        return ResponseEntity.created(URI.create("/api/v1/order-statuses/" + createdOrderStatus.getOrderStatusId())).body(createdOrderStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderStatusResponseDto> updateOrderStatusById(@PathVariable Long id, @RequestBody UpdateOrderStatusRequestDto updateOrderStatusRequestDto) {
        return ResponseEntity.ok(orderStatusService.updateOrderStatusById(id, updateOrderStatusRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderStatusById(@PathVariable Long id) {
        orderStatusService.deleteOrderStatusById(id);

        return ResponseEntity.ok("Deleted order status with id: " + id);
    }

}
