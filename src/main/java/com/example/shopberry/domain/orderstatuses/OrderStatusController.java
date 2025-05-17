package com.example.shopberry.domain.orderstatuses;

import com.example.shopberry.domain.orderstatuses.dto.CreateOrderStatusRequestDto;
import com.example.shopberry.domain.orderstatuses.dto.OrderStatusResponseDto;
import com.example.shopberry.domain.orderstatuses.dto.UpdateOrderStatusRequestDto;
import org.springframework.http.HttpStatus;
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
        List<OrderStatusResponseDto> orderStatusResponseDtoList = orderStatusService.getOrderStatuses();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderStatusResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderStatusResponseDto> getOrderStatusById(@PathVariable Long id) {
        OrderStatusResponseDto orderStatusResponseDto = orderStatusService.getOrderStatusById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderStatusResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<OrderStatusResponseDto> createOrderStatus(@RequestBody CreateOrderStatusRequestDto createOrderStatusRequestDto) {
        OrderStatusResponseDto createdOrderStatusResponseDto = orderStatusService.createOrderStatus(createOrderStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/order-statuses/" + createdOrderStatusResponseDto.getOrderStatusId()))
                .body(createdOrderStatusResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderStatusResponseDto> updateOrderStatusById(@PathVariable Long id, @RequestBody UpdateOrderStatusRequestDto updateOrderStatusRequestDto) {
        OrderStatusResponseDto updatedOrderStatusResponseDto = orderStatusService.updateOrderStatusById(id, updateOrderStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedOrderStatusResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderStatusById(@PathVariable Long id) {
        orderStatusService.deleteOrderStatusById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Order status with id " + id + " deleted successfully");
    }

}
