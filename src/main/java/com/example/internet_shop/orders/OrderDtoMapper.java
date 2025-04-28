package com.example.internet_shop.orders;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDtoMapper {

    public OrderDto toDto(Order Order) {
        OrderDto OrderDto = new OrderDto();

        OrderDto.setOrderId(Order.getOrderId());
        OrderDto.setCreatedAt(Order.getCreatedAt());
        OrderDto.setSentAt(Order.getSentAt());
        OrderDto.setOrderStatusId(Order.getOrderStatus().getOrderStatusId());
        OrderDto.setCustomerId(Order.getCustomer().getCustomerId());
        OrderDto.setShipmentTypeId(Order.getShipmentType().getShipmentTypeId());
        OrderDto.setShipmentIdentifier(Order.getShipmentIdentifier());
        OrderDto.setPaymentTypeId(Order.getPaymentType().getPaymentTypeId());
        OrderDto.setIsPaymentRecorded(Order.getIsPaymentRecorded());
        OrderDto.setIsInvoice(Order.getIsInvoice());

        return OrderDto;
    }

    public List<OrderDto> toDtoList(List<Order> Orders) {
        return Orders.stream()
                .map(this::toDto)
                .toList();
    }

}
