package com.example.shopberry.domain.orderproductstatuses;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.orderproductstatuses.dto.CreateOrderProductStatusRequestDto;
import com.example.shopberry.domain.orderproductstatuses.dto.OrderProductStatusResponseDto;
import com.example.shopberry.domain.orderproductstatuses.dto.UpdateOrderProductStatusRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderProductStatusController {

    private final OrderProductStatusService orderProductStatusService;

    @GetMapping("/order-product-statuses")
    public ResponseEntity<List<OrderProductStatusResponseDto>> getAllOrderProductStatuses() {
        List<OrderProductStatusResponseDto> orderProductStatusResponseDtoList = orderProductStatusService.getAllOrderProductStatuses();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductStatusResponseDtoList);
    }

    @GetMapping("/order-product-statuses/{orderProductStatusId}")
    public ResponseEntity<OrderProductStatusResponseDto> getOrderProductStatusById(@PathVariable Long orderProductStatusId) {
        OrderProductStatusResponseDto orderProductStatusResponseDto = orderProductStatusService.getOrderProductStatusById(orderProductStatusId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductStatusResponseDto);
    }

    @PostMapping("/order-product-statuses")
    public ResponseEntity<OrderProductStatusResponseDto> createOrderProductStatus(@RequestBody CreateOrderProductStatusRequestDto createOrderProductStatusRequestDto) {
        OrderProductStatusResponseDto createdOrderProductStatusResponseDto = orderProductStatusService.createOrderProductStatus(createOrderProductStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/order-product-statuses/" + createdOrderProductStatusResponseDto.getOrderProductStatusId()))
                .body(createdOrderProductStatusResponseDto);
    }

    @PatchMapping("/order-product-statuses/{orderProductStatusId}")
    public ResponseEntity<OrderProductStatusResponseDto> updateOrderProductStatusById(@PathVariable Long orderProductStatusId, @RequestBody UpdateOrderProductStatusRequestDto updateOrderProductStatusRequestDto) {
        OrderProductStatusResponseDto updatedOrderProductStatusResponseDto = orderProductStatusService.updateOrderProductStatusById(orderProductStatusId, updateOrderProductStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedOrderProductStatusResponseDto);
    }

    @DeleteMapping("/order-product-statuses/{orderProductStatusId}")
    public ResponseEntity<MessageResponseDto> deleteOrderProductStatusById(@PathVariable Long orderProductStatusId) {
        orderProductStatusService.deleteOrderProductStatusById(orderProductStatusId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Order product status with id " + orderProductStatusId + " deleted successfully"));
    }

}
