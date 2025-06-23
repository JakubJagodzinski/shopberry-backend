package com.example.shopberry.domain.complaints;

import com.example.shopberry.auth.access.manager.ComplaintAccessManager;
import com.example.shopberry.common.constants.messages.ComplaintMessages;
import com.example.shopberry.common.constants.messages.CustomerMessages;
import com.example.shopberry.common.constants.messages.OrderMessages;
import com.example.shopberry.common.constants.messages.ProductMessages;
import com.example.shopberry.domain.complaints.dto.ComplaintDtoMapper;
import com.example.shopberry.domain.complaints.dto.request.CreateComplaintRequestDto;
import com.example.shopberry.domain.complaints.dto.request.UpdateComplaintRequestDto;
import com.example.shopberry.domain.complaints.dto.response.ComplaintResponseDto;
import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.customers.CustomerRepository;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    private final ComplaintDtoMapper complaintDtoMapper;

    private final ComplaintAccessManager complaintAccessManager;
    private final CustomerRepository customerRepository;

    public List<ComplaintResponseDto> getAllComplaints() {
        return complaintDtoMapper.toDtoList(complaintRepository.findAll());
    }

    @Transactional
    public List<ComplaintResponseDto> getCustomerAllComplaints(UUID customerId) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        complaintAccessManager.checkCanReadCustomerAllComplaints(customer);

        return complaintDtoMapper.toDtoList(complaintRepository.findAllByOrder_Customer_UserId(customerId));
    }

    @Transactional
    public ComplaintResponseDto getComplaintById(Long complaintId) throws EntityNotFoundException {
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);

        if (complaint == null) {
            throw new EntityNotFoundException(ComplaintMessages.COMPLAINT_NOT_FOUND);
        }

        complaintAccessManager.checkCanReadComplaint(complaint);

        return complaintDtoMapper.toDto(complaint);
    }

    @Transactional
    public ComplaintResponseDto createComplaint(CreateComplaintRequestDto createComplaintRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Order order = orderRepository.findById(createComplaintRequestDto.getOrderId()).orElse(null);

        if (order == null) {
            throw new EntityNotFoundException(OrderMessages.ORDER_NOT_FOUND);
        }

        complaintAccessManager.checkCanCreateComplaint(order.getCustomer());

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

        complaintAccessManager.checkCanUpdateComplaint(complaint);

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
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);

        if (complaint == null) {
            throw new EntityNotFoundException(ComplaintMessages.COMPLAINT_NOT_FOUND);
        }

        complaintAccessManager.checkCanDeleteComplaint(complaint);

        complaintRepository.deleteById(complaintId);
    }

}
