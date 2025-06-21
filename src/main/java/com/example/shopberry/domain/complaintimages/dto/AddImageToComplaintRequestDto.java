package com.example.shopberry.domain.complaintimages.dto;

import com.example.shopberry.common.constants.messages.ComplaintMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddImageToComplaintRequestDto {

    @Schema(
            description = "Base64 representation of image",
            example = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII="
    )
    @NotNull(message = ComplaintMessages.COMPLAINT_IMAGE_CANNOT_BE_NULL)
    private byte[] image;

}
