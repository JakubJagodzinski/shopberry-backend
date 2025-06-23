package com.example.shopberry.domain.orderstatuses;

import com.example.shopberry.common.constants.messages.OrderStatusMessages;
import com.example.shopberry.domain.orderstatuses.dto.request.CreateOrderStatusRequestDto;
import com.example.shopberry.domain.orderstatuses.dto.OrderStatusDtoMapper;
import com.example.shopberry.domain.orderstatuses.dto.response.OrderStatusResponseDto;
import com.example.shopberry.domain.orderstatuses.dto.request.UpdateOrderStatusRequestDto;
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
    public OrderStatusResponseDto createOrderStatus(CreateOrderStatusRequestDto createOrderStatusRequestDto) throws EntityExistsException {
        String orderStatusName = createOrderStatusRequestDto.getOrderStatusName().trim();

        if (orderStatusRepository.existsByOrderStatusName(orderStatusName)) {
            throw new EntityExistsException(OrderStatusMessages.ORDER_STATUS_ALREADY_EXISTS);
        }

        OrderStatus orderStatus = new OrderStatus();

        orderStatus.setOrderStatusName(orderStatusName);
        orderStatus.setDescription(createOrderStatusRequestDto.getDescription().trim());

        return orderStatusDtoMapper.toDto(orderStatusRepository.save(orderStatus));
    }

    @Transactional
    public OrderStatusResponseDto updateOrderStatusById(Long orderStatusId, UpdateOrderStatusRequestDto updateOrderStatusRequestDto) throws EntityNotFoundException, EntityExistsException, IllegalArgumentException {
        OrderStatus orderStatus = orderStatusRepository.findById(orderStatusId).orElse(null);

        if (orderStatus == null) {
            throw new EntityNotFoundException(OrderStatusMessages.ORDER_STATUS_NOT_FOUND);
        }

        if (updateOrderStatusRequestDto.getOrderStatusName() != null) {
            String orderStatusName = updateOrderStatusRequestDto.getOrderStatusName().trim();

            OrderStatus otherOrderStatus = orderStatusRepository.findByOrderStatusName(orderStatusName).orElse(null);

            if (otherOrderStatus != null && !otherOrderStatus.getOrderStatusId().equals(orderStatusId)) {
                throw new EntityExistsException(OrderStatusMessages.ORDER_STATUS_ALREADY_EXISTS);
            }

            orderStatus.setOrderStatusName(orderStatusName);
        }

        if (updateOrderStatusRequestDto.getDescription() != null) {
            orderStatus.setDescription(updateOrderStatusRequestDto.getDescription().trim());
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
