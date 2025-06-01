package com.example.shopberry.domain.complaints;

import com.example.shopberry.domain.complaints.dto.ComplaintDtoMapper;
import com.example.shopberry.domain.complaints.dto.ComplaintResponseDto;
import com.example.shopberry.domain.complaints.dto.CreateComplaintRequestDto;
import com.example.shopberry.domain.complaints.dto.UpdateComplaintRequestDto;
import com.example.shopberry.domain.orderproducts.OrderProductId;
import com.example.shopberry.domain.orderproducts.OrderProductRepository;
import com.example.shopberry.domain.orders.Order;
import com.example.shopberry.domain.orders.OrderRepository;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    private final ComplaintDtoMapper complaintDtoMapper;

    private static final String COMPLAINT_NOT_FOUND_MESSAGE = "Complaint not found";
    private static final String ORDER_ID_CANNOT_BE_NULL_MESSAGE = "Order ID cannot be null";
    private static final String PRODUCT_ID_CANNOT_BE_NULL_MESSAGE = "Product ID cannot be null";
    private static final String ORDER_NOT_FOUND_MESSAGE = "Order not found";
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private static final String PRODUCT_DOES_NOT_BELONG_TO_THAT_ORDER_MESSAGE = "Product does not belong to that order";

    public List<ComplaintResponseDto> getComplaints() {
        return complaintDtoMapper.toDtoList(complaintRepository.findAll());
    }

    @Transactional
    public ComplaintResponseDto getComplaintById(Long id) throws EntityNotFoundException {
        Complaint complaint = complaintRepository.findById(id).orElse(null);

        if (complaint == null) {
            throw new EntityNotFoundException(COMPLAINT_NOT_FOUND_MESSAGE);
        }

        return complaintDtoMapper.toDto(complaint);
    }

    @Transactional
    public ComplaintResponseDto createComplaint(CreateComplaintRequestDto createComplaintRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        if (createComplaintRequestDto.getOrderId() == null) {
            throw new IllegalArgumentException(ORDER_ID_CANNOT_BE_NULL_MESSAGE);
        }

        if (createComplaintRequestDto.getProductId() == null) {
            throw new IllegalArgumentException(PRODUCT_ID_CANNOT_BE_NULL_MESSAGE);
        }

        Order order = orderRepository.findById(createComplaintRequestDto.getOrderId()).orElse(null);

        if (order == null) {
            throw new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE);
        }

        Product product = productRepository.findById(createComplaintRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        if (!orderProductRepository.existsById(new OrderProductId(createComplaintRequestDto.getOrderId(), createComplaintRequestDto.getProductId()))) {
            throw new IllegalArgumentException(PRODUCT_DOES_NOT_BELONG_TO_THAT_ORDER_MESSAGE);
        }

        Complaint complaint = new Complaint();

        complaint.setOrder(order);
        complaint.setProduct(product);
        complaint.setInfo(createComplaintRequestDto.getInfo());
        complaint.setFirstName(createComplaintRequestDto.getFirstName());
        complaint.setLastName(createComplaintRequestDto.getLastName());
        complaint.setNip(createComplaintRequestDto.getNip());
        complaint.setCity(createComplaintRequestDto.getCity());
        complaint.setPostalCode(createComplaintRequestDto.getPostalCode());
        complaint.setStreet(createComplaintRequestDto.getStreet());
        complaint.setHouseNumber(createComplaintRequestDto.getHouseNumber());
        complaint.setApartment(createComplaintRequestDto.getApartment());
        complaint.setPhoneNumber(createComplaintRequestDto.getPhoneNumber());

        return complaintDtoMapper.toDto(complaintRepository.save(complaint));
    }

    @Transactional
    public ComplaintResponseDto updateComplaintById(Long id, UpdateComplaintRequestDto updateComplaintRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Complaint complaint = complaintRepository.findById(id).orElse(null);

        if (complaint == null) {
            throw new EntityNotFoundException(COMPLAINT_NOT_FOUND_MESSAGE);
        }

        if (updateComplaintRequestDto.getProductId() != null) {
            Product product = productRepository.findById(updateComplaintRequestDto.getProductId()).orElse(null);

            if (product == null) {
                throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
            }

            if (!orderProductRepository.existsById(new OrderProductId(id, product.getProductId()))) {
                throw new IllegalArgumentException(PRODUCT_DOES_NOT_BELONG_TO_THAT_ORDER_MESSAGE);
            }

            complaint.setProduct(product);
        }

        if (updateComplaintRequestDto.getInfo() != null) {
            complaint.setInfo(updateComplaintRequestDto.getInfo());
        }

        if (updateComplaintRequestDto.getFirstName() != null) {
            complaint.setFirstName(updateComplaintRequestDto.getFirstName());
        }

        if (updateComplaintRequestDto.getLastName() != null) {
            complaint.setLastName(updateComplaintRequestDto.getLastName());
        }

        if (updateComplaintRequestDto.getNip() != null) {
            complaint.setNip(updateComplaintRequestDto.getNip());
        }

        if (updateComplaintRequestDto.getCity() != null) {
            complaint.setCity(updateComplaintRequestDto.getCity());
        }

        if (updateComplaintRequestDto.getPostalCode() != null) {
            complaint.setPostalCode(updateComplaintRequestDto.getPostalCode());
        }

        if (updateComplaintRequestDto.getStreet() != null) {
            complaint.setStreet(updateComplaintRequestDto.getStreet());
        }

        if (updateComplaintRequestDto.getHouseNumber() != null) {
            complaint.setHouseNumber(updateComplaintRequestDto.getHouseNumber());
        }

        if (updateComplaintRequestDto.getApartment() != null) {
            complaint.setApartment(updateComplaintRequestDto.getApartment());
        }

        if (updateComplaintRequestDto.getPhoneNumber() != null) {
            complaint.setPhoneNumber(updateComplaintRequestDto.getPhoneNumber());
        }

        return complaintDtoMapper.toDto(complaintRepository.save(complaint));
    }

    @Transactional
    public void deleteComplaintById(Long id) throws EntityNotFoundException {
        if (!complaintRepository.existsById(id)) {
            throw new EntityNotFoundException(COMPLAINT_NOT_FOUND_MESSAGE);
        }

        complaintRepository.deleteById(id);
    }

}
