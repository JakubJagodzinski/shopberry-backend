package com.example.shopberry.domain.orderstatuses;

import com.example.shopberry.common.constants.messages.OrderStatusMessages;
import com.example.shopberry.domain.orderstatuses.dto.CreateOrderStatusRequestDto;
import com.example.shopberry.domain.orderstatuses.dto.OrderStatusDtoMapper;
import com.example.shopberry.domain.orderstatuses.dto.OrderStatusResponseDto;
import com.example.shopberry.domain.orderstatuses.dto.UpdateOrderStatusRequestDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    private final OrderStatusDtoMapper orderStatusDtoMapper;

    public List<OrderStatusResponseDto> getAllOrderStatuses() {
        return orderStatusDtoMapper.toDtoList(orderStatusRepository.findAll());
    }

    @Transactional
    public OrderStatusResponseDto getOrderStatusById(Long orderStatusId) throws EntityNotFoundException {
        OrderStatus orderStatus = orderStatusRepository.findById(orderStatusId).orElse(null);

        if (orderStatus == null) {
            throw new EntityNotFoundException(OrderStatusMessages.ORDER_STATUS_NOT_FOUND);
        }

        return orderStatusDtoMapper.toDto(orderStatus);
    }

    @Transactional
    public OrderStatusResponseDto createOrderStatus(CreateOrderStatusRequestDto createOrderStatusRequestDto) throws EntityExistsException, IllegalArgumentException {
        if (orderStatusRepository.existsByOrderStatusName(createOrderStatusRequestDto.getOrderStatusName())) {
            throw new EntityExistsException(OrderStatusMessages.ORDER_STATUS_ALREADY_EXISTS);
        }

        if (createOrderStatusRequestDto.getOrderStatusName() == null) {
            throw new IllegalArgumentException(OrderStatusMessages.ORDER_STATUS_NAME_CANNOT_BE_NULL);
        }

        if (createOrderStatusRequestDto.getOrderStatusName().isEmpty()) {
            throw new IllegalArgumentException(OrderStatusMessages.ORDER_STATUS_NAME_CANNOT_BE_EMPTY);
        }

        OrderStatus orderStatus = new OrderStatus();

        orderStatus.setOrderStatusName(createOrderStatusRequestDto.getOrderStatusName());

        return orderStatusDtoMapper.toDto(orderStatusRepository.save(orderStatus));
    }

    @Transactional
    public OrderStatusResponseDto updateOrderStatusById(Long orderStatusId, UpdateOrderStatusRequestDto updateOrderStatusRequestDto) throws EntityNotFoundException, EntityExistsException, IllegalArgumentException {
        OrderStatus orderStatus = orderStatusRepository.findById(orderStatusId).orElse(null);

        if (orderStatus == null) {
            throw new EntityNotFoundException(OrderStatusMessages.ORDER_STATUS_NOT_FOUND);
        }

        if (updateOrderStatusRequestDto.getOrderStatusName() != null) {
            OrderStatus otherOrderStatus = orderStatusRepository.findByOrderStatusName(updateOrderStatusRequestDto.getOrderStatusName()).orElse(null);

            if (otherOrderStatus != null && !otherOrderStatus.getOrderStatusId().equals(orderStatusId)) {
                throw new EntityExistsException(OrderStatusMessages.ORDER_STATUS_ALREADY_EXISTS);
            }

            if (updateOrderStatusRequestDto.getOrderStatusName().isEmpty()) {
                throw new IllegalArgumentException(OrderStatusMessages.ORDER_STATUS_NAME_CANNOT_BE_EMPTY);
            }

            orderStatus.setOrderStatusName(updateOrderStatusRequestDto.getOrderStatusName());
        }

        return orderStatusDtoMapper.toDto(orderStatusRepository.save(orderStatus));
    }

    @Transactional
    public void deleteOrderStatusById(Long orderStatusId) throws EntityNotFoundException {
        if (!orderStatusRepository.existsById(orderStatusId)) {
            throw new EntityNotFoundException(OrderStatusMessages.ORDER_STATUS_NOT_FOUND);
        }

        orderStatusRepository.deleteById(orderStatusId);
    }

}
