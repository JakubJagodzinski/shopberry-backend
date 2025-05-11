package com.example.internet_shop.orderstatuses;

import com.example.internet_shop.orderstatuses.dto.CreateOrderStatusRequestDto;
import com.example.internet_shop.orderstatuses.dto.OrderStatusDtoMapper;
import com.example.internet_shop.orderstatuses.dto.OrderStatusResponseDto;
import com.example.internet_shop.orderstatuses.dto.UpdateOrderStatusRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    private final OrderStatusDtoMapper orderStatusDtoMapper;

    private final String ORDER_STATUS_NOT_FOUND_MESSAGE = "Order status not found";
    private final String ORDER_STATUS_ALREADY_EXISTS_MESSAGE = "Order status already exists";
    private final String ORDER_STATUS_NAME_CANNOT_BE_NULL_MESSAGE = "Order status name cannot be null";
    private final String ORDER_STATUS_NAME_CANNOT_BE_EMPTY_MESSAGE = "Order status name cannot be empty";

    public OrderStatusService(OrderStatusRepository orderStatusRepository, OrderStatusDtoMapper orderStatusDtoMapper) {
        this.orderStatusRepository = orderStatusRepository;
        this.orderStatusDtoMapper = orderStatusDtoMapper;
    }

    public List<OrderStatusResponseDto> getOrderStatuses() {
        return orderStatusDtoMapper.toDtoList(orderStatusRepository.findAll());
    }

    @Transactional
    public OrderStatusResponseDto getOrderStatusById(Long id) throws EntityNotFoundException {
        OrderStatus orderStatus = orderStatusRepository.findById(id).orElse(null);

        if (orderStatus == null) {
            throw new EntityNotFoundException(ORDER_STATUS_NOT_FOUND_MESSAGE);
        }

        return orderStatusDtoMapper.toDto(orderStatus);
    }

    @Transactional
    public OrderStatusResponseDto createOrderStatus(CreateOrderStatusRequestDto createOrderStatusRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        if (orderStatusRepository.existsByOrderStatusName(createOrderStatusRequestDto.getOrderStatusName())) {
            throw new EntityNotFoundException(ORDER_STATUS_ALREADY_EXISTS_MESSAGE);
        }

        if (createOrderStatusRequestDto.getOrderStatusName() == null) {
            throw new IllegalArgumentException(ORDER_STATUS_NAME_CANNOT_BE_NULL_MESSAGE);
        }

        if (createOrderStatusRequestDto.getOrderStatusName().isEmpty()) {
            throw new IllegalArgumentException(ORDER_STATUS_NAME_CANNOT_BE_EMPTY_MESSAGE);
        }

        OrderStatus orderStatus = new OrderStatus();

        orderStatus.setOrderStatusName(createOrderStatusRequestDto.getOrderStatusName());

        return orderStatusDtoMapper.toDto(orderStatusRepository.save(orderStatus));
    }

    @Transactional
    public OrderStatusResponseDto updateOrderStatusById(Long id, UpdateOrderStatusRequestDto updateOrderStatusRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        OrderStatus orderStatus = orderStatusRepository.findById(id).orElse(null);

        if (orderStatus == null) {
            throw new EntityNotFoundException(ORDER_STATUS_NOT_FOUND_MESSAGE);
        }

        if (updateOrderStatusRequestDto.getOrderStatusName() != null) {
            OrderStatus otherOrderStatus = orderStatusRepository.findByOrderStatusName(updateOrderStatusRequestDto.getOrderStatusName());

            if (otherOrderStatus != null && !otherOrderStatus.getOrderStatusId().equals(id)) {
                throw new IllegalArgumentException(ORDER_STATUS_ALREADY_EXISTS_MESSAGE);
            }

            if (updateOrderStatusRequestDto.getOrderStatusName().isEmpty()) {
                throw new IllegalArgumentException(ORDER_STATUS_NAME_CANNOT_BE_EMPTY_MESSAGE);
            }

            orderStatus.setOrderStatusName(updateOrderStatusRequestDto.getOrderStatusName());
        }

        return orderStatusDtoMapper.toDto(orderStatusRepository.save(orderStatus));
    }

    @Transactional
    public void deleteOrderStatusById(Long id) throws EntityNotFoundException {
        if (!orderStatusRepository.existsById(id)) {
            throw new EntityNotFoundException(ORDER_STATUS_NOT_FOUND_MESSAGE);
        }

        orderStatusRepository.deleteById(id);
    }

}
