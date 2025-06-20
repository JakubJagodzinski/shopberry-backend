package com.example.shopberry.domain.orderproductstatuses;

import com.example.shopberry.common.constants.messages.OrderProductStatusMessages;
import com.example.shopberry.domain.orderproductstatuses.dto.CreateOrderProductStatusRequestDto;
import com.example.shopberry.domain.orderproductstatuses.dto.OrderProductStatusDtoMapper;
import com.example.shopberry.domain.orderproductstatuses.dto.OrderProductStatusResponseDto;
import com.example.shopberry.domain.orderproductstatuses.dto.UpdateOrderProductStatusRequestDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProductStatusService {

    private final OrderProductStatusRepository orderProductStatusRepository;

    private final OrderProductStatusDtoMapper orderProductStatusDtoMapper;

    public List<OrderProductStatusResponseDto> getAllOrderProductStatuses() {
        return orderProductStatusDtoMapper.toDtoList(orderProductStatusRepository.findAll());
    }

    @Transactional
    public OrderProductStatusResponseDto getOrderProductStatusById(Long orderProductStatusId) throws EntityNotFoundException {
        OrderProductStatus orderProductStatus = orderProductStatusRepository.findById(orderProductStatusId).orElse(null);

        if (orderProductStatus == null) {
            throw new EntityNotFoundException(OrderProductStatusMessages.ORDER_PRODUCT_STATUS_NOT_FOUND);
        }

        return orderProductStatusDtoMapper.toDto(orderProductStatus);
    }

    @Transactional
    public OrderProductStatusResponseDto createOrderProductStatus(CreateOrderProductStatusRequestDto createOrderProductStatusRequestDto) throws EntityExistsException, IllegalArgumentException {
        if (orderProductStatusRepository.existsByStatusName(createOrderProductStatusRequestDto.getStatusName())) {
            throw new EntityExistsException(OrderProductStatusMessages.ORDER_PRODUCT_STATUS_ALREADY_EXISTS);
        }

        if (createOrderProductStatusRequestDto.getStatusName() == null) {
            throw new IllegalArgumentException(OrderProductStatusMessages.ORDER_PRODUCT_STATUS_NAME_CANNOT_BE_NULL);
        }

        if (createOrderProductStatusRequestDto.getStatusName().isEmpty()) {
            throw new IllegalArgumentException(OrderProductStatusMessages.ORDER_PRODUCT_STATUS_NAME_CANNOT_BE_EMPTY);
        }

        OrderProductStatus orderProductStatus = new OrderProductStatus();

        orderProductStatus.setStatusName(createOrderProductStatusRequestDto.getStatusName());

        return orderProductStatusDtoMapper.toDto(orderProductStatusRepository.save(orderProductStatus));
    }

    @Transactional
    public OrderProductStatusResponseDto updateOrderProductStatusById(Long orderProductStatusId, UpdateOrderProductStatusRequestDto updateOrderProductStatusRequestDto) throws EntityNotFoundException, EntityExistsException, IllegalArgumentException {
        OrderProductStatus orderProductStatus = orderProductStatusRepository.findById(orderProductStatusId).orElse(null);

        if (orderProductStatus == null) {
            throw new EntityNotFoundException(OrderProductStatusMessages.ORDER_PRODUCT_STATUS_NOT_FOUND);
        }

        if (updateOrderProductStatusRequestDto.getStatusName() != null) {
            OrderProductStatus otherOrderProductStatus = orderProductStatusRepository.findByStatusName(updateOrderProductStatusRequestDto.getStatusName()).orElse(null);

            if (otherOrderProductStatus != null && !otherOrderProductStatus.getOrderProductStatusId().equals(orderProductStatusId)) {
                throw new EntityExistsException(OrderProductStatusMessages.ORDER_PRODUCT_STATUS_ALREADY_EXISTS);
            }

            if (updateOrderProductStatusRequestDto.getStatusName().isEmpty()) {
                throw new IllegalArgumentException(OrderProductStatusMessages.ORDER_PRODUCT_STATUS_NAME_CANNOT_BE_EMPTY);
            }

            orderProductStatus.setStatusName(updateOrderProductStatusRequestDto.getStatusName());
        }

        return orderProductStatusDtoMapper.toDto(orderProductStatusRepository.save(orderProductStatus));
    }

    @Transactional
    public void deleteOrderProductStatusById(Long orderProductStatusId) throws EntityNotFoundException {
        OrderProductStatus orderProductStatus = orderProductStatusRepository.findById(orderProductStatusId).orElse(null);

        if (orderProductStatus == null) {
            throw new EntityNotFoundException(OrderProductStatusMessages.ORDER_PRODUCT_STATUS_NOT_FOUND);
        }

        orderProductStatusRepository.deleteById(orderProductStatusId);
    }

}
