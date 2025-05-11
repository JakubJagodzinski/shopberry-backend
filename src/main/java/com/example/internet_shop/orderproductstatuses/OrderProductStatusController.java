package com.example.internet_shop.orderproductstatuses;

import com.example.internet_shop.orderproductstatuses.dto.CreateOrderProductStatusRequestDto;
import com.example.internet_shop.orderproductstatuses.dto.OrderProductStatusResponseDto;
import com.example.internet_shop.orderproductstatuses.dto.UpdateOrderProductStatusRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-product-statuses")
public class OrderProductStatusController {

    private final OrderProductStatusService orderProductStatusService;

    public OrderProductStatusController(OrderProductStatusService orderProductStatusService) {
        this.orderProductStatusService = orderProductStatusService;
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderProductStatusResponseDto>> getOrderProductStatuses() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductStatusService.getOrderProductStatuses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderProductStatusResponseDto> getOrderProductStatusById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductStatusService.getOrderProductStatusById(id));
    }

    @PostMapping("/")
    public ResponseEntity<OrderProductStatusResponseDto> createOrderProductStatus(@RequestBody CreateOrderProductStatusRequestDto createOrderProductStatusRequestDto) {
        OrderProductStatusResponseDto createdOrderProductStatus = orderProductStatusService.createOrderProductStatus(createOrderProductStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/api/v1/order-product-statuses/" + createdOrderProductStatus.getOrderProductStatusId())
                .body(createdOrderProductStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderProductStatusResponseDto> updateOrderProductStatusById(@PathVariable Long id, @RequestBody UpdateOrderProductStatusRequestDto updateOrderProductStatusRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductStatusService.updateOrderProductStatusById(id, updateOrderProductStatusRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderProductStatusById(@PathVariable Long id) {
        orderProductStatusService.deleteOrderProductStatusById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Order product status with id " + id + " deleted successfully");
    }

}
