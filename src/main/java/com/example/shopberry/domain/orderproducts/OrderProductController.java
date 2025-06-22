package com.example.shopberry.domain.orderproducts;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.orderproducts.dto.OrderProductResponseDto;
import com.example.shopberry.user.Permission;
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

    @CheckPermission(Permission.ORDER_PRODUCT_READ)
    @GetMapping("/orders/{orderId}/products/{productId}")
    public ResponseEntity<OrderProductResponseDto> getOrderProductById(@PathVariable Long orderId, @PathVariable Long productId) {
        OrderProductResponseDto orderProductResponseDto = orderProductService.getOrderProductById(orderId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductResponseDto);
    }

    @CheckPermission(Permission.ORDER_PRODUCT_READ_ALL)
    @GetMapping("/orders/{orderId}/products")
    public ResponseEntity<List<OrderProductResponseDto>> getOrderAllProducts(@PathVariable Long orderId) {
        List<OrderProductResponseDto> orderProductResponseDtoList = orderProductService.getOrderAllProducts(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductResponseDtoList);
    }

}
