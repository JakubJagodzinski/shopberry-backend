package com.example.shopberry.domain.complaintimages.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"image_id", "complaint_id", "image"})
public class ComplaintImageResponseDto {

    @JsonProperty("image_id")
    private Long imageId;

    @JsonProperty("complaint_id")
    private Long complaintId;

    private byte[] image;

}
