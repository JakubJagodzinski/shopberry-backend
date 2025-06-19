package com.example.shopberry.domain.orderstatuses;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.orderstatuses.dto.CreateOrderStatusRequestDto;
import com.example.shopberry.domain.orderstatuses.dto.OrderStatusResponseDto;
import com.example.shopberry.domain.orderstatuses.dto.UpdateOrderStatusRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order-statuses")
@RequiredArgsConstructor
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    @GetMapping
    public ResponseEntity<List<OrderStatusResponseDto>> getOrderStatuses() {
        List<OrderStatusResponseDto> orderStatusResponseDtoList = orderStatusService.getOrderStatuses();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderStatusResponseDtoList);
    }

    @GetMapping("/{orderStatusId}")
    public ResponseEntity<OrderStatusResponseDto> getOrderStatusById(@PathVariable Long orderStatusId) {
        OrderStatusResponseDto orderStatusResponseDto = orderStatusService.getOrderStatusById(orderStatusId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderStatusResponseDto);
    }

    @PostMapping
    public ResponseEntity<OrderStatusResponseDto> createOrderStatus(@RequestBody CreateOrderStatusRequestDto createOrderStatusRequestDto) {
        OrderStatusResponseDto createdOrderStatusResponseDto = orderStatusService.createOrderStatus(createOrderStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/order-statuses/" + createdOrderStatusResponseDto.getOrderStatusId()))
                .body(createdOrderStatusResponseDto);
    }

    @PatchMapping("/{orderStatusId}")
    public ResponseEntity<OrderStatusResponseDto> updateOrderStatusById(@PathVariable Long orderStatusId, @RequestBody UpdateOrderStatusRequestDto updateOrderStatusRequestDto) {
        OrderStatusResponseDto updatedOrderStatusResponseDto = orderStatusService.updateOrderStatusById(orderStatusId, updateOrderStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedOrderStatusResponseDto);
    }

    @DeleteMapping("/{orderStatusId}")
    public ResponseEntity<MessageResponseDto> deleteOrderStatusById(@PathVariable Long orderStatusId) {
        orderStatusService.deleteOrderStatusById(orderStatusId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Order status with id " + orderStatusId + " deleted successfully"));
    }

}
