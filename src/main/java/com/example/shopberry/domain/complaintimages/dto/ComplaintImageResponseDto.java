package com.example.shopberry.domain.complaintimages.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintImageResponseDto {

    @JsonProperty("image_id")
    private Long imageId;

    @JsonProperty("complaint_id")
    private Long complaintId;

    private byte[] image;

}
