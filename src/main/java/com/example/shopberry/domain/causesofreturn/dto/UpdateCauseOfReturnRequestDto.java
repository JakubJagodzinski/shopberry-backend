package com.example.shopberry.domain.causesofreturn.dto;

import com.example.shopberry.common.constants.messages.CauseOfReturnMessages;
import com.example.shopberry.common.validation.NotEmptyIfPresent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCauseOfReturnRequestDto {

    @Schema(
            description = "Cause of return unique name",
            example = "Product broken"
    )
    @NotEmptyIfPresent(
            message = CauseOfReturnMessages.CAUSE_CANNOT_BE_EMPTY,
            notBlankMessage = CauseOfReturnMessages.CAUSE_CANNOT_CONTAIN_ONLY_WHITESPACES
    )
    private String cause;

}
