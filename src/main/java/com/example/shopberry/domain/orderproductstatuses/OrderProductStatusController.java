package com.example.shopberry.domain.orderproductstatuses;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.orderproductstatuses.dto.request.CreateOrderProductStatusRequestDto;
import com.example.shopberry.domain.orderproductstatuses.dto.request.UpdateOrderProductStatusRequestDto;
import com.example.shopberry.domain.orderproductstatuses.dto.response.OrderProductStatusResponseDto;
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
public class OrderProductStatusController {

    private final OrderProductStatusService orderProductStatusService;

    @Operation(summary = "Get all order product statuses")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of order product statuses",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderProductStatusResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.ORDER_PRODUCT_STATUS_READ_ALL)
    @GetMapping("/order-product-statuses")
    public ResponseEntity<List<OrderProductStatusResponseDto>> getAllOrderProductStatuses() {
        List<OrderProductStatusResponseDto> orderProductStatusResponseDtoList = orderProductStatusService.getAllOrderProductStatuses();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductStatusResponseDtoList);
    }

    @Operation(summary = "Get order product status by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order product status found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderProductStatusResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order product status not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ORDER_PRODUCT_STATUS_READ)
    @GetMapping("/order-product-statuses/{orderProductStatusId}")
    public ResponseEntity<OrderProductStatusResponseDto> getOrderProductStatusById(@PathVariable Long orderProductStatusId) {
        OrderProductStatusResponseDto orderProductStatusResponseDto = orderProductStatusService.getOrderProductStatusById(orderProductStatusId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductStatusResponseDto);
    }

    @Operation(summary = "Create a new order product status")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Order product status created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderProductStatusResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Order product status with that name already exists",
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
    @CheckPermission(Permission.ORDER_PRODUCT_STATUS_UPDATE)
    @PostMapping("/order-product-statuses")
    public ResponseEntity<OrderProductStatusResponseDto> createOrderProductStatus(@Valid @RequestBody CreateOrderProductStatusRequestDto createOrderProductStatusRequestDto) {
        OrderProductStatusResponseDto createdOrderProductStatusResponseDto = orderProductStatusService.createOrderProductStatus(createOrderProductStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/order-product-statuses/" + createdOrderProductStatusResponseDto.getOrderProductStatusId()))
                .body(createdOrderProductStatusResponseDto);
    }

    @Operation(summary = "Update order product status by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order product status updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderProductStatusResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Order product status with than name already exists",
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
                    description = "Order product status not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ORDER_PRODUCT_STATUS_UPDATE)
    @PatchMapping("/order-product-statuses/{orderProductStatusId}")
    public ResponseEntity<OrderProductStatusResponseDto> updateOrderProductStatusById(@PathVariable Long orderProductStatusId, @Valid @RequestBody UpdateOrderProductStatusRequestDto updateOrderProductStatusRequestDto) {
        OrderProductStatusResponseDto updatedOrderProductStatusResponseDto = orderProductStatusService.updateOrderProductStatusById(orderProductStatusId, updateOrderProductStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedOrderProductStatusResponseDto);
    }

    @Operation(summary = "Delete order product status by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Order product status deleted"
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
                    description = "Order product status not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ORDER_PRODUCT_STATUS_DELETE)
    @DeleteMapping("/order-product-statuses/{orderProductStatusId}")
    public ResponseEntity<Void> deleteOrderProductStatusById(@PathVariable Long orderProductStatusId) {
        orderProductStatusService.deleteOrderProductStatusById(orderProductStatusId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
