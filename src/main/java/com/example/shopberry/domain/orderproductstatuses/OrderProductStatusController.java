package com.example.shopberry.domain.orderproductstatuses;

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
@RequestMapping("/api/v1/order-product-statuses")
@RequiredArgsConstructor
public class OrderProductStatusController {

    private final OrderProductStatusService orderProductStatusService;

    @GetMapping("/")
    public ResponseEntity<List<OrderProductStatusResponseDto>> getOrderProductStatuses() {
        List<OrderProductStatusResponseDto> orderProductStatusResponseDtoList = orderProductStatusService.getOrderProductStatuses();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductStatusResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderProductStatusResponseDto> getOrderProductStatusById(@PathVariable Long id) {
        OrderProductStatusResponseDto orderProductStatusResponseDto = orderProductStatusService.getOrderProductStatusById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderProductStatusResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<OrderProductStatusResponseDto> createOrderProductStatus(@RequestBody CreateOrderProductStatusRequestDto createOrderProductStatusRequestDto) {
        OrderProductStatusResponseDto createdOrderProductStatusResponseDto = orderProductStatusService.createOrderProductStatus(createOrderProductStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/order-product-statuses/" + createdOrderProductStatusResponseDto.getOrderProductStatusId()))
                .body(createdOrderProductStatusResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderProductStatusResponseDto> updateOrderProductStatusById(@PathVariable Long id, @RequestBody UpdateOrderProductStatusRequestDto updateOrderProductStatusRequestDto) {
        OrderProductStatusResponseDto updatedOrderProductStatusResponseDto = orderProductStatusService.updateOrderProductStatusById(id, updateOrderProductStatusRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedOrderProductStatusResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderProductStatusById(@PathVariable Long id) {
        orderProductStatusService.deleteOrderProductStatusById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Order product status with id " + id + " deleted successfully");
    }

}
