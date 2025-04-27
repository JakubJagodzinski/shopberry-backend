package com.example.internet_shop.orderstatuses;

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

    public List<OrderStatusDto> getOrderStatuses() {
        return orderStatusDtoMapper.toDtoList(orderStatusRepository.findAll());
    }

    @Transactional
    public OrderStatusDto getOrderStatusById(Long id) throws EntityNotFoundException {
        if (!orderStatusRepository.existsById(id)) {
            throw new EntityNotFoundException(ORDER_STATUS_NOT_FOUND_MESSAGE);
        }

        return orderStatusDtoMapper.toDto(orderStatusRepository.getReferenceById(id));
    }

    @Transactional
    public OrderStatusDto createOrderStatus(CreateOrderStatusDto createOrderStatusDto) throws EntityNotFoundException, IllegalArgumentException {
        if (orderStatusRepository.existsByOrderStatusName(createOrderStatusDto.getOrderStatusName())) {
            throw new EntityNotFoundException(ORDER_STATUS_ALREADY_EXISTS_MESSAGE);
        }

        if (createOrderStatusDto.getOrderStatusName() == null) {
            throw new IllegalArgumentException(ORDER_STATUS_NAME_CANNOT_BE_NULL_MESSAGE);
        }

        if (createOrderStatusDto.getOrderStatusName().isEmpty()) {
            throw new IllegalArgumentException(ORDER_STATUS_NAME_CANNOT_BE_EMPTY_MESSAGE);
        }

        OrderStatus orderStatus = new OrderStatus();

        orderStatus.setOrderStatusName(createOrderStatusDto.getOrderStatusName());

        return orderStatusDtoMapper.toDto(orderStatusRepository.save(orderStatus));
    }

    @Transactional
    public OrderStatusDto updateOrderStatusById(Long id, UpdateOrderStatusDto updateOrderStatusDto) throws EntityNotFoundException, IllegalArgumentException {
        if (!orderStatusRepository.existsById(id)) {
            throw new EntityNotFoundException(ORDER_STATUS_NOT_FOUND_MESSAGE);
        }

        OrderStatus orderStatus = orderStatusRepository.getReferenceById(id);

        if (updateOrderStatusDto.getOrderStatusName() != null) {
            OrderStatus otherOrderStatus = orderStatusRepository.findByOrderStatusName(updateOrderStatusDto.getOrderStatusName());

            if (otherOrderStatus != null && !otherOrderStatus.getOrderStatusId().equals(id)) {
                throw new IllegalArgumentException(ORDER_STATUS_ALREADY_EXISTS_MESSAGE);
            }

            if (updateOrderStatusDto.getOrderStatusName().isEmpty()) {
                throw new IllegalArgumentException(ORDER_STATUS_NAME_CANNOT_BE_EMPTY_MESSAGE);
            }

            orderStatus.setOrderStatusName(updateOrderStatusDto.getOrderStatusName());
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
