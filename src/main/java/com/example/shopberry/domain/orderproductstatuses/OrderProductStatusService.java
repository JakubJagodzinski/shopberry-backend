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
    public OrderProductStatusResponseDto createOrderProductStatus(CreateOrderProductStatusRequestDto createOrderProductStatusRequestDto) throws EntityExistsException {
        String statusName = createOrderProductStatusRequestDto.getStatusName().trim();

        if (orderProductStatusRepository.existsByStatusName(statusName)) {
            throw new EntityExistsException(OrderProductStatusMessages.ORDER_PRODUCT_STATUS_ALREADY_EXISTS);
        }

        OrderProductStatus orderProductStatus = new OrderProductStatus();

        orderProductStatus.setStatusName(statusName);
        orderProductStatus.setDescription(createOrderProductStatusRequestDto.getDescription().trim());

        return orderProductStatusDtoMapper.toDto(orderProductStatusRepository.save(orderProductStatus));
    }

    @Transactional
    public OrderProductStatusResponseDto updateOrderProductStatusById(Long orderProductStatusId, UpdateOrderProductStatusRequestDto updateOrderProductStatusRequestDto) throws EntityNotFoundException, EntityExistsException {
        OrderProductStatus orderProductStatus = orderProductStatusRepository.findById(orderProductStatusId).orElse(null);

        if (orderProductStatus == null) {
            throw new EntityNotFoundException(OrderProductStatusMessages.ORDER_PRODUCT_STATUS_NOT_FOUND);
        }

        if (updateOrderProductStatusRequestDto.getStatusName() != null) {
            String statusName = updateOrderProductStatusRequestDto.getStatusName().trim();

            OrderProductStatus otherOrderProductStatus = orderProductStatusRepository.findByStatusName(statusName).orElse(null);

            if (otherOrderProductStatus != null && !otherOrderProductStatus.getOrderProductStatusId().equals(orderProductStatusId)) {
                throw new EntityExistsException(OrderProductStatusMessages.ORDER_PRODUCT_STATUS_ALREADY_EXISTS);
            }

            orderProductStatus.setStatusName(statusName);
        }

        if (updateOrderProductStatusRequestDto.getDescription() != null) {
            orderProductStatus.setDescription(updateOrderProductStatusRequestDto.getDescription().trim());
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
