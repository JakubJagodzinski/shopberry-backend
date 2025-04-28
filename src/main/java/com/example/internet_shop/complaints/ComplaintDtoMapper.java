package com.example.internet_shop.complaints;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComplaintDtoMapper {

    public ComplaintDto toDto(Complaint Complaint) {
        ComplaintDto ComplaintDto = new ComplaintDto();

        ComplaintDto.setComplaintId(Complaint.getComplaintId());
        ComplaintDto.setOrderId(Complaint.getOrder().getOrderId());
        ComplaintDto.setProductId(Complaint.getProduct().getProductId());
        ComplaintDto.setInfo(Complaint.getInfo());
        ComplaintDto.setFirstName(Complaint.getFirstName());
        ComplaintDto.setLastName(Complaint.getLastName());
        ComplaintDto.setNip(Complaint.getNip());
        ComplaintDto.setCity(Complaint.getCity());
        ComplaintDto.setPostalCode(Complaint.getPostalCode());
        ComplaintDto.setStreet(Complaint.getStreet());
        ComplaintDto.setHouseNumber(Complaint.getHouseNumber());
        ComplaintDto.setApartment(Complaint.getApartment());
        ComplaintDto.setPhoneNumber(Complaint.getPhoneNumber());

        return ComplaintDto;
    }

    public List<ComplaintDto> toDtoList(List<Complaint> Complaints) {
        return Complaints.stream()
                .map(this::toDto)
                .toList();
    }

}
