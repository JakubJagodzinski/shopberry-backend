package com.example.shopberry.domain.complaints.dto;

import com.example.shopberry.domain.complaints.Complaint;
import com.example.shopberry.domain.orders.dto.OrderDtoMapper;
import com.example.shopberry.domain.products.dto.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ComplaintDtoMapper {

    private final OrderDtoMapper orderDtoMapper;
    private final ProductDtoMapper productDtoMapper;

    public ComplaintResponseDto toDto(Complaint complaint) {
        if (complaint == null) {
            return null;
        }

        ComplaintResponseDto ComplaintResponseDto = new ComplaintResponseDto();

        ComplaintResponseDto.setComplaintId(complaint.getComplaintId());
        ComplaintResponseDto.setOrder(orderDtoMapper.toDto(complaint.getOrder()));
        ComplaintResponseDto.setProduct(productDtoMapper.toDto(complaint.getProduct()));
        ComplaintResponseDto.setInfo(complaint.getInfo());
        ComplaintResponseDto.setFirstName(complaint.getFirstName());
        ComplaintResponseDto.setLastName(complaint.getLastName());
        ComplaintResponseDto.setNip(complaint.getNip());
        ComplaintResponseDto.setCity(complaint.getCity());
        ComplaintResponseDto.setPostalCode(complaint.getPostalCode());
        ComplaintResponseDto.setStreet(complaint.getStreet());
        ComplaintResponseDto.setHouseNumber(complaint.getHouseNumber());
        ComplaintResponseDto.setApartment(complaint.getApartment());
        ComplaintResponseDto.setPhoneNumber(complaint.getPhoneNumber());

        return ComplaintResponseDto;
    }

    public List<ComplaintResponseDto> toDtoList(List<Complaint> complaintList) {
        return complaintList.stream()
                .map(this::toDto)
                .toList();
    }

}
