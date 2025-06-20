package com.example.shopberry.domain.orderstatuses.dto;

import com.example.shopberry.domain.orderstatuses.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderStatusDtoMapper {

    public OrderStatusResponseDto toDto(OrderStatus orderStatus) {
        OrderStatusResponseDto OrderStatusResponseDto = new OrderStatusResponseDto();

        OrderStatusResponseDto.setOrderStatusId(orderStatus.getOrderStatusId());
        OrderStatusResponseDto.setOrderStatusName(orderStatus.getOrderStatusName());

        return OrderStatusResponseDto;
    }

    public List<OrderStatusResponseDto> toDtoList(List<OrderStatus> orderStatusList) {
        return orderStatusList.stream()
                .map(this::toDto)
                .toList();
    }

}
