package com.example.shopberry.domain.categoriesattributes.dto;

import com.example.shopberry.common.constants.messages.AttributeMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class AssignAttributeToCategoryRequestDto {

    @Schema(
            description = "Id of attribute to assign to category. Each attribute can be assigned once",
            example = "1"
    )
    @NotNull(message = AttributeMessages.ATTRIBUTE_ID_CANNOT_BE_NULL)
    @JsonProperty("attribute_id")
    private Long attributeId;

}
