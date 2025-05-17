package com.example.shopberry.domain.complaints.dto;

import com.example.shopberry.domain.complaints.Complaint;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComplaintDtoMapper {

    public ComplaintResponseDto toDto(Complaint Complaint) {
        ComplaintResponseDto ComplaintResponseDto = new ComplaintResponseDto();

        ComplaintResponseDto.setComplaintId(Complaint.getComplaintId());
        ComplaintResponseDto.setOrderId(Complaint.getOrder().getOrderId());
        ComplaintResponseDto.setProductId(Complaint.getProduct().getProductId());
        ComplaintResponseDto.setInfo(Complaint.getInfo());
        ComplaintResponseDto.setFirstName(Complaint.getFirstName());
        ComplaintResponseDto.setLastName(Complaint.getLastName());
        ComplaintResponseDto.setNip(Complaint.getNip());
        ComplaintResponseDto.setCity(Complaint.getCity());
        ComplaintResponseDto.setPostalCode(Complaint.getPostalCode());
        ComplaintResponseDto.setStreet(Complaint.getStreet());
        ComplaintResponseDto.setHouseNumber(Complaint.getHouseNumber());
        ComplaintResponseDto.setApartment(Complaint.getApartment());
        ComplaintResponseDto.setPhoneNumber(Complaint.getPhoneNumber());

        return ComplaintResponseDto;
    }

    public List<ComplaintResponseDto> toDtoList(List<Complaint> Complaints) {
        return Complaints.stream()
                .map(this::toDto)
                .toList();
    }

}
