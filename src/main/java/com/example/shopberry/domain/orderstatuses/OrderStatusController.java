package com.example.shopberry.domain.orderstatuses;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.orderstatuses.dto.request.CreateOrderStatusRequestDto;
import com.example.shopberry.domain.orderstatuses.dto.request.UpdateOrderStatusRequestDto;
import com.example.shopberry.domain.orderstatuses.dto.response.OrderStatusResponseDto;
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

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    @Operation(summary = "Get all order statuses")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of order statuses",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderStatusResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.ORDER_STATUS_READ_ALL)
    @GetMapping("/order-statuses")
    public ResponseEntity<List<OrderStatusResponseDto>> getAllOrderStatuses() {
        List<OrderStatusResponseDto> orderStatusResponseDtoList = orderStatusService.getAllOrderStatuses();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderStatusResponseDtoList);
    }

    @Operation(summary = "Get order status by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order status found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderStatusResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order status not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ORDER_STATUS_READ)
    @GetMapping("/order-statuses/{orderStatusId}")
    public ResponseEntity<OrderStatusResponseDto> getOrderStatusById(@PathVariable Long orderStatusId) {
        OrderStatusResponseDto orderStatusResponseDto = orderStatusService.getOrderStatusById(orderStatusId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderStatusResponseDto);
    }

    @Operation(summary = "Create a new order status")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Order status created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderStatusResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Order status with that name already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
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
    @CheckPermission(Permission.ORDER_STATUS_CREATE)
    @PostMapping("/order-statuses")
    public ResponseEntity<OrderStatusResponseDto> createOrderStatus(@Valid @RequestBody CreateOrderStatusRequestDto createOrderStatusRequestDto) {
        OrderStatusResponseDto createdOrderStatusResponseDto = orderStatusService.createOrderStatus(createOrderStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/order-statuses/" + createdOrderStatusResponseDto.getOrderStatusId()))
                .body(createdOrderStatusResponseDto);
    }

    @Operation(summary = "Update order status by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order status updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderStatusResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Order status with than name already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
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
                    description = "Order status not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ORDER_STATUS_UPDATE)
    @PatchMapping("/order-statuses/{orderStatusId}")
    public ResponseEntity<OrderStatusResponseDto> updateOrderStatusById(@PathVariable Long orderStatusId, @Valid @RequestBody UpdateOrderStatusRequestDto updateOrderStatusRequestDto) {
        OrderStatusResponseDto updatedOrderStatusResponseDto = orderStatusService.updateOrderStatusById(orderStatusId, updateOrderStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedOrderStatusResponseDto);
    }

    @Operation(summary = "Delete order status by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Order status deleted"
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
                    description = "Order status not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ORDER_STATUS_DELETE)
    @DeleteMapping("/order-statuses/{orderStatusId}")
    public ResponseEntity<Void> deleteOrderStatusById(@PathVariable Long orderStatusId) {
        orderStatusService.deleteOrderStatusById(orderStatusId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
