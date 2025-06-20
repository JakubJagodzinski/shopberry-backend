package com.example.shopberry.domain.complaintimages.dto;


import com.example.shopberry.domain.complaintimages.ComplaintImage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComplaintImageDtoMapper {

    public ComplaintImageResponseDto toDto(ComplaintImage complaintImage) {
        ComplaintImageResponseDto ComplaintImageResponseDto = new ComplaintImageResponseDto();

        ComplaintImageResponseDto.setImageId(complaintImage.getImageId());
        ComplaintImageResponseDto.setComplaintId(complaintImage.getComplaint().getComplaintId());
        ComplaintImageResponseDto.setImage(complaintImage.getImage());

        return ComplaintImageResponseDto;
    }

    public List<ComplaintImageResponseDto> toDtoList(List<ComplaintImage> complaintImageList) {
        return complaintImageList.stream()
                .map(this::toDto)
                .toList();
    }

}
