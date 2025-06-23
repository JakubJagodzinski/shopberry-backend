package com.example.shopberry.domain.orderstatuses;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.orderstatuses.dto.request.CreateOrderStatusRequestDto;
import com.example.shopberry.domain.orderstatuses.dto.request.UpdateOrderStatusRequestDto;
import com.example.shopberry.domain.orderstatuses.dto.response.OrderStatusResponseDto;
import com.example.shopberry.user.Permission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
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
    public ResponseEntity<OrderStatusResponseDto> createOrderStatus(@Valid @RequestBody CreateOrderStatusRequestDto createOrderStatusRequestDto) {
        OrderStatusResponseDto createdOrderStatusResponseDto = orderStatusService.createOrderStatus(createOrderStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/order-statuses/" + createdOrderStatusResponseDto.getOrderStatusId()))
                .body(createdOrderStatusResponseDto);
    }

    @CheckPermission(Permission.ORDER_STATUS_UPDATE)
    @PatchMapping("/order-statuses/{orderStatusId}")
    public ResponseEntity<OrderStatusResponseDto> updateOrderStatusById(@PathVariable Long orderStatusId, @Valid @RequestBody UpdateOrderStatusRequestDto updateOrderStatusRequestDto) {
        OrderStatusResponseDto updatedOrderStatusResponseDto = orderStatusService.updateOrderStatusById(orderStatusId, updateOrderStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedOrderStatusResponseDto);
    }

    @CheckPermission(Permission.ORDER_STATUS_DELETE)
    @DeleteMapping("/order-statuses/{orderStatusId}")
    public ResponseEntity<Void> deleteOrderStatusById(@PathVariable Long orderStatusId) {
        orderStatusService.deleteOrderStatusById(orderStatusId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
