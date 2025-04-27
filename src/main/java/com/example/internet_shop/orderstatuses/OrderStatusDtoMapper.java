package com.example.internet_shop.orderstatuses;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderStatusDtoMapper {

    public OrderStatusDto toDto(OrderStatus orderStatus) {
        OrderStatusDto OrderStatusDto = new OrderStatusDto();

        OrderStatusDto.setOrderStatusId(orderStatus.getOrderStatusId());
        OrderStatusDto.setOrderStatusName(orderStatus.getOrderStatusName());

        return OrderStatusDto;
    }

    public List<OrderStatusDto> toDtoList(List<OrderStatus> orderStatus) {
        return orderStatus.stream()
                .map(this::toDto)
                .toList();
    }

}
