package com.example.shopberry.orderproductstatuses.dto;

import com.example.shopberry.orderproductstatuses.OrderProductStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProductStatusDtoMapper {

    public OrderProductStatusResponseDto toDto(OrderProductStatus orderProductStatus) {
        OrderProductStatusResponseDto orderProductStatusResponseDto = new OrderProductStatusResponseDto();

        orderProductStatusResponseDto.setOrderProductStatusId(orderProductStatus.getOrderProductStatusId());
        orderProductStatusResponseDto.setStatusName(orderProductStatus.getStatusName());

        return orderProductStatusResponseDto;
    }

    public List<OrderProductStatusResponseDto> toDtoList(List<OrderProductStatus> orderProductStatuses) {
        return orderProductStatuses.stream()
                .map(this::toDto)
                .toList();
    }

}
