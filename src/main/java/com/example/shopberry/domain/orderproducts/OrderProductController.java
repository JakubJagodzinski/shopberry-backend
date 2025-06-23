package com.example.shopberry.domain.orderproducts;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.orderproducts.dto.response.OrderProductResponseDto;
import com.example.shopberry.exception.ApiError;
import com.example.shopberry.user.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class OrderProductController {

    private final OrderProductService orderProductService;

    @Operation(summary = "Get product from order by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product from order found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderProductResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found / product not found / product doesn't belong to that order",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ORDER_PRODUCT_READ)
    @GetMapping("/orders/{orderId}/products/{productId}")
    public ResponseEntity<OrderProductResponseDto> getOrderProductById(@PathVariable Long orderId, @PathVariable Long productId) {
        OrderProductResponseDto orderProductResponseDto = orderProductService.getOrderProductById(orderId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductResponseDto);
    }

    @Operation(summary = "Get order all products")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of order products",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrderProductResponseDto.class)
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
    @CheckPermission(Permission.ORDER_PRODUCT_READ_ALL)
    @GetMapping("/orders/{orderId}/products")
    public ResponseEntity<List<OrderProductResponseDto>> getOrderAllProducts(@PathVariable Long orderId) {
        List<OrderProductResponseDto> orderProductResponseDtoList = orderProductService.getOrderAllProducts(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductResponseDtoList);
    }

}
