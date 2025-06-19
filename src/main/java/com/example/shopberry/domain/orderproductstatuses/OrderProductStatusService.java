package com.example.shopberry.domain.orderproductstatuses;

import com.example.shopberry.domain.orderproducts.OrderProductRepository;
import com.example.shopberry.domain.orderproductstatuses.dto.CreateOrderProductStatusRequestDto;
import com.example.shopberry.domain.orderproductstatuses.dto.OrderProductStatusDtoMapper;
import com.example.shopberry.domain.orderproductstatuses.dto.OrderProductStatusResponseDto;
import com.example.shopberry.domain.orderproductstatuses.dto.UpdateOrderProductStatusRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProductStatusService {

    private final OrderProductStatusRepository orderProductStatusRepository;
    private final OrderProductRepository orderProductRepository;

    private final OrderProductStatusDtoMapper orderProductStatusDtoMapper;

    private static final String ORDER_PRODUCT_STATUS_NOT_FOUND_MESSAGE = "Order product status not found";
    private static final String ORDER_PRODUCT_STATUS_ALREADY_EXISTS_MESSAGE = "Order product status already exists";
    private static final String ORDER_PRODUCT_STATUS_NAME_CANNOT_BE_NULL_MESSAGE = "Order product status name cannot be null";
    private static final String ORDER_PRODUCT_STATUS_NAME_CANNOT_BE_EMPTY_MESSAGE = "Order product status name cannot be empty";
    private static final String ORDER_PRODUCT_STATUS_IS_IN_USE_MESSAGE = "Order product status is in use and cannot be deleted";

    public List<OrderProductStatusResponseDto> getAllOrderProductStatuses() {
        return orderProductStatusDtoMapper.toDtoList(orderProductStatusRepository.findAll());
    }

    @Transactional
    public OrderProductStatusResponseDto getOrderProductStatusById(Long orderProductStatusId) throws EntityNotFoundException {
        OrderProductStatus orderProductStatus = orderProductStatusRepository.findById(orderProductStatusId).orElse(null);

        if (orderProductStatus == null) {
            throw new EntityNotFoundException(ORDER_PRODUCT_STATUS_NOT_FOUND_MESSAGE);
        }

        return orderProductStatusDtoMapper.toDto(orderProductStatus);
    }

    @Transactional
    public OrderProductStatusResponseDto createOrderProductStatus(CreateOrderProductStatusRequestDto createOrderProductStatusRequestDto) throws IllegalArgumentException {
        if (orderProductStatusRepository.existsByStatusName(createOrderProductStatusRequestDto.getStatusName())) {
            throw new IllegalArgumentException(ORDER_PRODUCT_STATUS_ALREADY_EXISTS_MESSAGE);
        }

        if (createOrderProductStatusRequestDto.getStatusName() == null) {
            throw new IllegalArgumentException(ORDER_PRODUCT_STATUS_NAME_CANNOT_BE_NULL_MESSAGE);
        }

        if (createOrderProductStatusRequestDto.getStatusName().isEmpty()) {
            throw new IllegalArgumentException(ORDER_PRODUCT_STATUS_NAME_CANNOT_BE_EMPTY_MESSAGE);
        }

        OrderProductStatus orderProductStatus = new OrderProductStatus();

        orderProductStatus.setStatusName(createOrderProductStatusRequestDto.getStatusName());

        return orderProductStatusDtoMapper.toDto(orderProductStatusRepository.save(orderProductStatus));
    }

    @Transactional
    public OrderProductStatusResponseDto updateOrderProductStatusById(Long orderProductStatusId, UpdateOrderProductStatusRequestDto updateOrderProductStatusRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        OrderProductStatus orderProductStatus = orderProductStatusRepository.findById(orderProductStatusId).orElse(null);

        if (orderProductStatus == null) {
            throw new EntityNotFoundException(ORDER_PRODUCT_STATUS_NOT_FOUND_MESSAGE);
        }

        if (updateOrderProductStatusRequestDto.getStatusName() != null) {
            OrderProductStatus otherOrderProductStatus = orderProductStatusRepository.findByStatusName(updateOrderProductStatusRequestDto.getStatusName()).orElse(null);

            if (otherOrderProductStatus != null && !otherOrderProductStatus.getOrderProductStatusId().equals(orderProductStatusId)) {
                throw new IllegalArgumentException(ORDER_PRODUCT_STATUS_ALREADY_EXISTS_MESSAGE);
            }

            if (updateOrderProductStatusRequestDto.getStatusName().isEmpty()) {
                throw new IllegalArgumentException(ORDER_PRODUCT_STATUS_NAME_CANNOT_BE_EMPTY_MESSAGE);
            }

            orderProductStatus.setStatusName(updateOrderProductStatusRequestDto.getStatusName());
        }

        return orderProductStatusDtoMapper.toDto(orderProductStatusRepository.save(orderProductStatus));
    }

    @Transactional
    public void deleteOrderProductStatusById(Long orderProductStatusId) throws EntityNotFoundException {
        OrderProductStatus orderProductStatus = orderProductStatusRepository.findById(orderProductStatusId).orElse(null);

        if (orderProductStatus == null) {
            throw new EntityNotFoundException(ORDER_PRODUCT_STATUS_NOT_FOUND_MESSAGE);
        }

        if (orderProductRepository.existsByOrderProductStatus_OrderProductStatusId(orderProductStatusId)) {
            throw new IllegalArgumentException(ORDER_PRODUCT_STATUS_IS_IN_USE_MESSAGE);
        }

        orderProductStatusRepository.deleteById(orderProductStatusId);
    }

}
