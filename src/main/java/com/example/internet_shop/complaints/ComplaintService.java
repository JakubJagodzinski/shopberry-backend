package com.example.internet_shop.complaints;

import com.example.internet_shop.orders.OrderRepository;
import com.example.internet_shop.products.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final ComplaintDtoMapper complaintDtoMapper;

    private final String COMPLAINT_NOT_FOUND_MESSAGE = "Complaint not found";
    private final String ORDER_ID_CANNOT_BE_NULL_MESSAGE = "Order ID cannot be null";
    private final String PRODUCT_ID_CANNOT_BE_NULL_MESSAGE = "Product ID cannot be null";
    private final String ORDER_NOT_FOUND_MESSAGE = "Order not found";
    private final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private final String PRODUCT_DOES_NOT_BELONG_TO_THAT_ORDER_MESSAGE = "Product does not belong to that order";

    public ComplaintService(ComplaintRepository complaintRepository, ComplaintDtoMapper complaintDtoMapper, OrderRepository orderRepository, ProductRepository productRepository) {
        this.complaintRepository = complaintRepository;
        this.complaintDtoMapper = complaintDtoMapper;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<ComplaintDto> getComplaints() {
        return complaintDtoMapper.toDtoList(complaintRepository.findAll());
    }

    @Transactional
    public ComplaintDto getComplaintById(Long id) throws EntityNotFoundException {
        if (!complaintRepository.existsById(id)) {
            throw new EntityNotFoundException(COMPLAINT_NOT_FOUND_MESSAGE);
        }

        return complaintDtoMapper.toDto(complaintRepository.getReferenceById(id));
    }

    @Transactional
    public ComplaintDto createComplaint(CreateComplaintDto createComplaintDto) throws EntityNotFoundException, IllegalArgumentException {
        if (createComplaintDto.getOrderId() == null) {
            throw new IllegalArgumentException(ORDER_ID_CANNOT_BE_NULL_MESSAGE);
        }

        if (createComplaintDto.getProductId() == null) {
            throw new IllegalArgumentException(PRODUCT_ID_CANNOT_BE_NULL_MESSAGE);
        }

        if (!orderRepository.existsById(createComplaintDto.getOrderId())) {
            throw new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE);
        }

        if (!productRepository.existsById(createComplaintDto.getProductId())) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        // TODO check if product belongs to order

        Complaint complaint = new Complaint();

        complaint.setOrder(orderRepository.getReferenceById(createComplaintDto.getOrderId()));
        complaint.setProduct(productRepository.getReferenceById(createComplaintDto.getProductId()));
        complaint.setInfo(createComplaintDto.getInfo());
        complaint.setFirstName(createComplaintDto.getFirstName());
        complaint.setLastName(createComplaintDto.getLastName());
        complaint.setNip(createComplaintDto.getNip());
        complaint.setCity(createComplaintDto.getCity());
        complaint.setPostalCode(createComplaintDto.getPostalCode());
        complaint.setStreet(createComplaintDto.getStreet());
        complaint.setHouseNumber(createComplaintDto.getHouseNumber());
        complaint.setApartment(createComplaintDto.getApartment());
        complaint.setPhoneNumber(createComplaintDto.getPhoneNumber());

        return complaintDtoMapper.toDto(complaintRepository.save(complaint));
    }

    @Transactional
    public ComplaintDto updateComplaintById(Long id, UpdateComplaintDto updateComplaintDto) throws EntityNotFoundException, IllegalArgumentException {
        if (!complaintRepository.existsById(id)) {
            throw new EntityNotFoundException(COMPLAINT_NOT_FOUND_MESSAGE);
        }

        Complaint complaint = complaintRepository.getReferenceById(id);

        if (updateComplaintDto.getProductId() != null) {
            // TODO check if product belongs to order
            if (!productRepository.existsById(updateComplaintDto.getProductId())) {
                throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
            }

            complaint.setProduct(productRepository.getReferenceById(updateComplaintDto.getProductId()));
        }

        if (updateComplaintDto.getInfo() != null) {
            complaint.setInfo(updateComplaintDto.getInfo());
        }

        if (updateComplaintDto.getFirstName() != null) {
            complaint.setFirstName(updateComplaintDto.getFirstName());
        }

        if (updateComplaintDto.getLastName() != null) {
            complaint.setLastName(updateComplaintDto.getLastName());
        }

        if (updateComplaintDto.getNip() != null) {
            complaint.setNip(updateComplaintDto.getNip());
        }

        if (updateComplaintDto.getCity() != null) {
            complaint.setCity(updateComplaintDto.getCity());
        }

        if (updateComplaintDto.getPostalCode() != null) {
            complaint.setPostalCode(updateComplaintDto.getPostalCode());
        }

        if (updateComplaintDto.getStreet() != null) {
            complaint.setStreet(updateComplaintDto.getStreet());
        }

        if (updateComplaintDto.getHouseNumber() != null) {
            complaint.setHouseNumber(updateComplaintDto.getHouseNumber());
        }

        if (updateComplaintDto.getApartment() != null) {
            complaint.setApartment(updateComplaintDto.getApartment());
        }

        if (updateComplaintDto.getPhoneNumber() != null) {
            complaint.setPhoneNumber(updateComplaintDto.getPhoneNumber());
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
