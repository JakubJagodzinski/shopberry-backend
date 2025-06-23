package com.example.shopberry.domain.orderstatuses.dto;

import com.example.shopberry.domain.orderstatuses.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderStatusDtoMapper {

    public OrderStatusResponseDto toDto(OrderStatus orderStatus) {
        if (orderStatus == null) {
            return null;
        }

        OrderStatusResponseDto orderStatusResponseDto = new OrderStatusResponseDto();

        orderStatusResponseDto.setOrderStatusId(orderStatus.getOrderStatusId());
        orderStatusResponseDto.setOrderStatusName(orderStatus.getOrderStatusName());
        orderStatusResponseDto.setDescription(orderStatus.getDescription());

        return orderStatusResponseDto;
    }

    public List<OrderStatusResponseDto> toDtoList(List<OrderStatus> orderStatusList) {
        return orderStatusList.stream()
                .map(this::toDto)
                .toList();
    }

}
