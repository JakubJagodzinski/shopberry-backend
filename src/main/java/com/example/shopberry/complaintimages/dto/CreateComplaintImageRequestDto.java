package com.example.shopberry.complaintimages.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateComplaintImageRequestDto {

    private Long id;

    private Long complaintId;

    private byte[] image;

}
