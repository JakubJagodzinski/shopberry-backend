package com.example.shopberry.domain.orderstatuses;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.orderstatuses.dto.CreateOrderStatusRequestDto;
import com.example.shopberry.domain.orderstatuses.dto.OrderStatusResponseDto;
import com.example.shopberry.domain.orderstatuses.dto.UpdateOrderStatusRequestDto;
import com.example.shopberry.user.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    @CheckPermission(Permission.ORDER_STATUS_READ_ALL)
    @GetMapping("/order-statuses")
    public ResponseEntity<List<OrderStatusResponseDto>> getAllOrderStatuses() {
        List<OrderStatusResponseDto> orderStatusResponseDtoList = orderStatusService.getAllOrderStatuses();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderStatusResponseDtoList);
    }

    @CheckPermission(Permission.ORDER_STATUS_READ)
    @GetMapping("/order-statuses/{orderStatusId}")
    public ResponseEntity<OrderStatusResponseDto> getOrderStatusById(@PathVariable Long orderStatusId) {
        OrderStatusResponseDto orderStatusResponseDto = orderStatusService.getOrderStatusById(orderStatusId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderStatusResponseDto);
    }

    @CheckPermission(Permission.ORDER_STATUS_CREATE)
    @PostMapping("/order-statuses")
    public ResponseEntity<OrderStatusResponseDto> createOrderStatus(@RequestBody CreateOrderStatusRequestDto createOrderStatusRequestDto) {
        OrderStatusResponseDto createdOrderStatusResponseDto = orderStatusService.createOrderStatus(createOrderStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/order-statuses/" + createdOrderStatusResponseDto.getOrderStatusId()))
                .body(createdOrderStatusResponseDto);
    }

    @CheckPermission(Permission.ORDER_STATUS_UPDATE)
    @PatchMapping("/order-statuses/{orderStatusId}")
    public ResponseEntity<OrderStatusResponseDto> updateOrderStatusById(@PathVariable Long orderStatusId, @RequestBody UpdateOrderStatusRequestDto updateOrderStatusRequestDto) {
        OrderStatusResponseDto updatedOrderStatusResponseDto = orderStatusService.updateOrderStatusById(orderStatusId, updateOrderStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedOrderStatusResponseDto);
    }

    @CheckPermission(Permission.ORDER_STATUS_DELETE)
    @DeleteMapping("/order-statuses/{orderStatusId}")
    public ResponseEntity<MessageResponseDto> deleteOrderStatusById(@PathVariable Long orderStatusId) {
        orderStatusService.deleteOrderStatusById(orderStatusId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Order status with id " + orderStatusId + " deleted successfully"));
    }

}
