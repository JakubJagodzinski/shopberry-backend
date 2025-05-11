package com.example.internet_shop.orderproductstatuses;

import com.example.internet_shop.orderproducts.OrderProductRepository;
import com.example.internet_shop.orderproductstatuses.dto.CreateOrderProductStatusRequestDto;
import com.example.internet_shop.orderproductstatuses.dto.OrderProductStatusDtoMapper;
import com.example.internet_shop.orderproductstatuses.dto.OrderProductStatusResponseDto;
import com.example.internet_shop.orderproductstatuses.dto.UpdateOrderProductStatusRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductStatusService {

    private final OrderProductStatusRepository orderProductStatusRepository;
    private final OrderProductRepository orderProductRepository;

    private final OrderProductStatusDtoMapper orderProductStatusDtoMapper;

    private final String ORDER_PRODUCT_STATUS_NOT_FOUND_MESSAGE = "Order product status not found";
    private final String ORDER_PRODUCT_STATUS_ALREADY_EXISTS_MESSAGE = "Order product status already exists";
    private final String ORDER_PRODUCT_STATUS_NAME_CANNOT_BE_NULL_MESSAGE = "Order product status name cannot be null";
    private final String ORDER_PRODUCT_STATUS_NAME_CANNOT_BE_EMPTY_MESSAGE = "Order product status name cannot be empty";
    private final String ORDER_PRODUCT_STATUS_IS_IN_USE_MESSAGE = "Order product status is in use and cannot be deleted";

    public OrderProductStatusService(OrderProductStatusRepository orderProductStatusRepository, OrderProductRepository orderProductRepository, OrderProductStatusDtoMapper orderProductStatusDtoMapper) {
        this.orderProductStatusRepository = orderProductStatusRepository;
        this.orderProductRepository = orderProductRepository;
        this.orderProductStatusDtoMapper = orderProductStatusDtoMapper;
    }

    public List<OrderProductStatusResponseDto> getOrderProductStatuses() {
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
