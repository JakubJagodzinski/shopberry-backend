package com.example.shopberry.domain.complaints;

import com.example.shopberry.common.constants.messages.ComplaintMessages;
import com.example.shopberry.common.constants.messages.OrderMessages;
import com.example.shopberry.common.constants.messages.ProductMessages;
import com.example.shopberry.domain.complaints.dto.ComplaintDtoMapper;
import com.example.shopberry.domain.complaints.dto.response.ComplaintResponseDto;
import com.example.shopberry.domain.complaints.dto.request.CreateComplaintRequestDto;
import com.example.shopberry.domain.complaints.dto.request.UpdateComplaintRequestDto;
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

    public List<ComplaintResponseDto> getAllComplaints() {
        return complaintDtoMapper.toDtoList(complaintRepository.findAll());
    }

    @Transactional
    public ComplaintResponseDto getComplaintById(Long complaintId) throws EntityNotFoundException {
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);

        if (complaint == null) {
            throw new EntityNotFoundException(ComplaintMessages.COMPLAINT_NOT_FOUND);
        }

        return complaintDtoMapper.toDto(complaint);
    }

    @Transactional
    public ComplaintResponseDto createComplaint(CreateComplaintRequestDto createComplaintRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Order order = orderRepository.findById(createComplaintRequestDto.getOrderId()).orElse(null);

        if (order == null) {
            throw new EntityNotFoundException(OrderMessages.ORDER_NOT_FOUND);
        }

        Product product = productRepository.findById(createComplaintRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        if (!orderProductRepository.existsById(new OrderProductId(createComplaintRequestDto.getOrderId(), createComplaintRequestDto.getProductId()))) {
            throw new IllegalArgumentException(ProductMessages.PRODUCT_DOES_NOT_BELONG_TO_THAT_ORDER);
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
    public ComplaintResponseDto updateComplaintById(Long complaintId, UpdateComplaintRequestDto updateComplaintRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);

        if (complaint == null) {
            throw new EntityNotFoundException(ComplaintMessages.COMPLAINT_NOT_FOUND);
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
    public void deleteComplaintById(Long complaintId) throws EntityNotFoundException {
        if (!complaintRepository.existsById(complaintId)) {
            throw new EntityNotFoundException(ComplaintMessages.COMPLAINT_NOT_FOUND);
        }

        complaintRepository.deleteById(complaintId);
    }

}
