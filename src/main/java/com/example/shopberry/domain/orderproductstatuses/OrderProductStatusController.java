package com.example.shopberry.domain.orderproductstatuses;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.orderproductstatuses.dto.request.CreateOrderProductStatusRequestDto;
import com.example.shopberry.domain.orderproductstatuses.dto.response.OrderProductStatusResponseDto;
import com.example.shopberry.domain.orderproductstatuses.dto.request.UpdateOrderProductStatusRequestDto;
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
public class OrderProductStatusController {

    private final OrderProductStatusService orderProductStatusService;

    @CheckPermission(Permission.ORDER_PRODUCT_STATUS_READ_ALL)
    @GetMapping("/order-product-statuses")
    public ResponseEntity<List<OrderProductStatusResponseDto>> getAllOrderProductStatuses() {
        List<OrderProductStatusResponseDto> orderProductStatusResponseDtoList = orderProductStatusService.getAllOrderProductStatuses();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductStatusResponseDtoList);
    }

    @CheckPermission(Permission.ORDER_PRODUCT_STATUS_READ)
    @GetMapping("/order-product-statuses/{orderProductStatusId}")
    public ResponseEntity<OrderProductStatusResponseDto> getOrderProductStatusById(@PathVariable Long orderProductStatusId) {
        OrderProductStatusResponseDto orderProductStatusResponseDto = orderProductStatusService.getOrderProductStatusById(orderProductStatusId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductStatusResponseDto);
    }

    @CheckPermission(Permission.ORDER_PRODUCT_STATUS_UPDATE)
    @PostMapping("/order-product-statuses")
    public ResponseEntity<OrderProductStatusResponseDto> createOrderProductStatus(@Valid @RequestBody CreateOrderProductStatusRequestDto createOrderProductStatusRequestDto) {
        OrderProductStatusResponseDto createdOrderProductStatusResponseDto = orderProductStatusService.createOrderProductStatus(createOrderProductStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/order-product-statuses/" + createdOrderProductStatusResponseDto.getOrderProductStatusId()))
                .body(createdOrderProductStatusResponseDto);
    }

    @CheckPermission(Permission.ORDER_PRODUCT_STATUS_UPDATE)
    @PatchMapping("/order-product-statuses/{orderProductStatusId}")
    public ResponseEntity<OrderProductStatusResponseDto> updateOrderProductStatusById(@PathVariable Long orderProductStatusId, @Valid @RequestBody UpdateOrderProductStatusRequestDto updateOrderProductStatusRequestDto) {
        OrderProductStatusResponseDto updatedOrderProductStatusResponseDto = orderProductStatusService.updateOrderProductStatusById(orderProductStatusId, updateOrderProductStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedOrderProductStatusResponseDto);
    }

    @CheckPermission(Permission.ORDER_PRODUCT_STATUS_DELETE)
    @DeleteMapping("/order-product-statuses/{orderProductStatusId}")
    public ResponseEntity<MessageResponseDto> deleteOrderProductStatusById(@PathVariable Long orderProductStatusId) {
        orderProductStatusService.deleteOrderProductStatusById(orderProductStatusId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Order product status with id " + orderProductStatusId + " deleted successfully"));
    }

}
