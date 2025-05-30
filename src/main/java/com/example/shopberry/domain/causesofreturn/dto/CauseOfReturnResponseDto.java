package com.example.shopberry.domain.causesofreturn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CauseOfReturnResponseDto {

    @JsonProperty("cause_of_return_id")
    private Long causeOfReturnId;

    private String cause;

}
