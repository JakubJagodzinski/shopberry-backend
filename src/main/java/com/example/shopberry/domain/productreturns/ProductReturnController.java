package com.example.shopberry.domain.productreturns;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.productreturns.dto.request.CreateProductReturnRequestDto;
import com.example.shopberry.domain.productreturns.dto.response.ProductReturnResponseDto;
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
public class ProductReturnController {

    private final ProductReturnService productReturnService;

    @Operation(summary = "Get product return by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product return found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductReturnResponseDto.class)
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
                    description = "Promotion return not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
    })
    @CheckPermission(Permission.PRODUCT_RETURN_READ)
    @GetMapping("/product-returns/{productReturnId}")
    public ResponseEntity<ProductReturnResponseDto> getProductReturnById(@PathVariable Long productReturnId) {
        ProductReturnResponseDto productReturnResponseDto = productReturnService.getProductReturnById(productReturnId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productReturnResponseDto);
    }

    @Operation(summary = "Get order all product returns")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of order all product returns",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductReturnResponseDto.class)
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
            ),
    })
    @CheckPermission(Permission.ORDER_PRODUCT_RETURN_READ_ALL)
    @GetMapping("/orders/{orderId}/product-returns")
    public ResponseEntity<List<ProductReturnResponseDto>> getOrderAllProductReturns(@PathVariable Long orderId) {
        List<ProductReturnResponseDto> productReturnResponseDtoList = productReturnService.getOrderAllProductReturns(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productReturnResponseDtoList);
    }

    @Operation(summary = "Create product return for order")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Product return created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductReturnResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Product return already exists / product does not belong to that order",
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
                    description = "Order / product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
    })
    @CheckPermission(Permission.PRODUCT_RETURN_CREATE)
    @PostMapping("/orders/{orderId}/product-returns")
    public ResponseEntity<ProductReturnResponseDto> createProductReturn(@PathVariable Long orderId, @Valid @RequestBody CreateProductReturnRequestDto createProductReturnRequestDto) {
        ProductReturnResponseDto createdProductReturnResponseDto = productReturnService.createProductReturn(orderId, createProductReturnRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/orders/" + orderId + "/product-returns/" + createdProductReturnResponseDto.getProductReturnId()))
                .body(createdProductReturnResponseDto);
    }

    @Operation(summary = "Delete product return by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Product return deleted successfully"
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
                    description = "Product return not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
    })
    @CheckPermission(Permission.PRODUCT_RETURN_DELETE)
    @DeleteMapping("/product-returns/{productReturnId}")
    public ResponseEntity<Void> deleteProductReturnById(@PathVariable Long productReturnId) {
        productReturnService.deleteProductReturnById(productReturnId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
