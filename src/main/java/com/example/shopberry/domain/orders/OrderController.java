package com.example.shopberry.domain.orders;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.orders.dto.request.CreateOrderRequestDto;
import com.example.shopberry.domain.orders.dto.request.UpdateOrderRequestDto;
import com.example.shopberry.domain.orders.dto.response.OrderResponseDto;
import com.example.shopberry.exception.ApiError;
import com.example.shopberry.user.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all orders")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of orders",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ORDER_READ_ALL)
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orderResponseDtoList = orderService.getAllOrders();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderResponseDtoList);
    }

    @Operation(summary = "Get order by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ORDER_READ)
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId) {
        OrderResponseDto orderResponseDto = orderService.getOrderById(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderResponseDto);
    }

    @Operation(summary = "Get customer all orders")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of customer's orders",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.CUSTOMER_ORDER_READ_ALL)
    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<List<OrderResponseDto>> getCustomerAllOrders(@PathVariable UUID customerId) {
        List<OrderResponseDto> orderResponseDtoList = orderService.getCustomerAllOrders(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderResponseDtoList);
    }

    @Operation(summary = "Create order for customer")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Created order",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ORDER_CREATE)
    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody CreateOrderRequestDto createOrderRequestDto) {
        OrderResponseDto createdOrderResponseDto = orderService.createOrder(createOrderRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/orders/" + createdOrderResponseDto.getOrderId()))
                .body(createdOrderResponseDto);
    }

    @Operation(summary = "Update order by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Updated order",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ORDER_UPDATE)
    @PatchMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrderById(@PathVariable Long orderId, @Valid @RequestBody UpdateOrderRequestDto updateOrderRequestDto) {
        OrderResponseDto updatedOrderResponseDto = orderService.updateOrderById(orderId, updateOrderRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedOrderResponseDto);
    }

    @Operation(summary = "Delete order by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Order deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order / shipment type / payment type not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ORDER_DELETE)
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long orderId) {
        orderService.deleteOrderById(orderId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
