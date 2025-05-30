package com.example.shopberry.domain.orders.dto;

import com.example.shopberry.domain.orders.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDtoMapper {

    public OrderResponseDto toDto(Order Order) {
        OrderResponseDto OrderResponseDto = new OrderResponseDto();

        OrderResponseDto.setOrderId(Order.getOrderId());
        OrderResponseDto.setCreatedAt(Order.getCreatedAt());
        OrderResponseDto.setSentAt(Order.getSentAt());
        OrderResponseDto.setOrderStatusId(Order.getOrderStatus().getOrderStatusId());
        OrderResponseDto.setCustomerId(Order.getCustomer().getId());
        OrderResponseDto.setShipmentTypeId(Order.getShipmentType().getShipmentTypeId());
        OrderResponseDto.setShipmentIdentifier(Order.getShipmentIdentifier());
        OrderResponseDto.setPaymentTypeId(Order.getPaymentType().getPaymentTypeId());
        OrderResponseDto.setIsPaymentRecorded(Order.getIsPaymentRecorded());
        OrderResponseDto.setIsInvoice(Order.getIsInvoice());

        return OrderResponseDto;
    }

    public List<OrderResponseDto> toDtoList(List<Order> Orders) {
        return Orders.stream()
                .map(this::toDto)
                .toList();
    }

}
