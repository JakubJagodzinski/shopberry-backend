package com.example.shopberry.domain.orders;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.orders.dto.request.CreateOrderRequestDto;
import com.example.shopberry.domain.orders.dto.request.UpdateOrderRequestDto;
import com.example.shopberry.domain.orders.dto.response.OrderResponseDto;
import com.example.shopberry.user.Permission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @CheckPermission(Permission.ORDER_READ_ALL)
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orderResponseDtoList = orderService.getAllOrders();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderResponseDtoList);
    }

    @CheckPermission(Permission.ORDER_READ)
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId) {
        OrderResponseDto orderResponseDto = orderService.getOrderById(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderResponseDto);
    }

    @CheckPermission(Permission.CUSTOMER_ORDER_READ_ALL)
    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<List<OrderResponseDto>> getCustomerAllOrders(@PathVariable UUID customerId) {
        List<OrderResponseDto> orderResponseDtoList = orderService.getCustomerAllOrders(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderResponseDtoList);
    }

    @CheckPermission(Permission.ORDER_CREATE)
    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody CreateOrderRequestDto createOrderRequestDto) {
        OrderResponseDto createdOrderResponseDto = orderService.createOrder(createOrderRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/orders/" + createdOrderResponseDto.getOrderId()))
                .body(createdOrderResponseDto);
    }

    @CheckPermission(Permission.ORDER_UPDATE)
    @PatchMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrderById(@PathVariable Long orderId, @Valid @RequestBody UpdateOrderRequestDto updateOrderRequestDto) {
        OrderResponseDto updatedOrderResponseDto = orderService.updateOrderById(orderId, updateOrderRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedOrderResponseDto);
    }

    @CheckPermission(Permission.ORDER_DELETE)
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<MessageResponseDto> deleteOrderById(@PathVariable Long orderId) {
        orderService.deleteOrderById(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Order with id " + orderId + " deleted successfully"));
    }

}
