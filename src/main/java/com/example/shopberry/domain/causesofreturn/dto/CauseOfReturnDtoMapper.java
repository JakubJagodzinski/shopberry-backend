package com.example.shopberry.domain.causesofreturn.dto;

import com.example.shopberry.domain.causesofreturn.CauseOfReturn;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CauseOfReturnDtoMapper {

    public CauseOfReturnResponseDto toDto(CauseOfReturn causeOfReturn) {
        CauseOfReturnResponseDto causeOfReturnResponseDto = new CauseOfReturnResponseDto();

        causeOfReturnResponseDto.setCauseOfReturnId(causeOfReturn.getCauseOfReturnId());
        causeOfReturnResponseDto.setCause(causeOfReturn.getCause());

        return causeOfReturnResponseDto;
    }

    public List<CauseOfReturnResponseDto> toDtoList(List<CauseOfReturn> causesOfReturns) {
        return causesOfReturns.stream()
                .map(this::toDto)
                .toList();
    }

}
