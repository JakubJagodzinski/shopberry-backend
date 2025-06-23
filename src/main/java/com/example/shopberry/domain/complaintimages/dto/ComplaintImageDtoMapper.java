package com.example.shopberry.domain.complaintimages.dto;


import com.example.shopberry.domain.complaintimages.ComplaintImage;
import com.example.shopberry.domain.complaintimages.dto.response.ComplaintImageResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComplaintImageDtoMapper {

    public ComplaintImageResponseDto toDto(ComplaintImage complaintImage) {
        if (complaintImage == null) {
            return null;
        }

        ComplaintImageResponseDto complaintImageResponseDto = new ComplaintImageResponseDto();

        complaintImageResponseDto.setImageId(complaintImage.getImageId());
        if (complaintImage.getComplaint() != null) {
            complaintImageResponseDto.setComplaintId(complaintImage.getComplaint().getComplaintId());
        } else {
            complaintImageResponseDto.setComplaintId(null);
        }
        complaintImageResponseDto.setImage(complaintImage.getImage());

        return complaintImageResponseDto;
    }

    public List<ComplaintImageResponseDto> toDtoList(List<ComplaintImage> complaintImageList) {
        return complaintImageList.stream()
                .map(this::toDto)
                .toList();
    }

}
