package com.example.shopberry.domain.orders.dto;

import com.example.shopberry.domain.orders.Order;
import com.example.shopberry.domain.orderstatuses.dto.OrderStatusDtoMapper;
import com.example.shopberry.domain.paymenttypes.dto.PaymentTypeDtoMapper;
import com.example.shopberry.domain.shipmenttypes.dto.ShipmentTypeDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderDtoMapper {

    private final ShipmentTypeDtoMapper shipmentTypeDtoMapper;
    private final PaymentTypeDtoMapper paymentTypeDtoMapper;
    private final OrderStatusDtoMapper orderStatusDtoMapper;

    public OrderResponseDto toDto(Order order) {
        if (order == null) {
            return null;
        }

        OrderResponseDto OrderResponseDto = new OrderResponseDto();

        OrderResponseDto.setOrderId(order.getOrderId());
        OrderResponseDto.setCreatedAt(order.getCreatedAt());
        OrderResponseDto.setSentAt(order.getSentAt());
        OrderResponseDto.setOrderStatus(orderStatusDtoMapper.toDto(order.getOrderStatus()));
        OrderResponseDto.setCustomerId(order.getCustomer().getUserId());
        OrderResponseDto.setShipmentType(shipmentTypeDtoMapper.toDto(order.getShipmentType()));
        OrderResponseDto.setShipmentIdentifier(order.getShipmentIdentifier());
        OrderResponseDto.setPaymentType(paymentTypeDtoMapper.toDto(order.getPaymentType()));
        OrderResponseDto.setIsPaymentRecorded(order.getIsPaymentRecorded());
        OrderResponseDto.setIsInvoice(order.getIsInvoice());

        return OrderResponseDto;
    }

    public List<OrderResponseDto> toDtoList(List<Order> orderList) {
        return orderList.stream()
                .map(this::toDto)
                .toList();
    }

}
