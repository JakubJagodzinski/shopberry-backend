package com.example.internet_shop.causesofreturn;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CauseOfReturnDtoMapper {

    public CauseOfReturnDto toDto(CauseOfReturn causeOfReturn) {
        CauseOfReturnDto causeOfReturnDto = new CauseOfReturnDto();

        causeOfReturnDto.setCauseOfReturnId(causeOfReturn.getCauseOfReturnId());
        causeOfReturnDto.setCause(causeOfReturn.getCause());

        return causeOfReturnDto;
    }

    public List<CauseOfReturnDto> toDtoList(List<CauseOfReturn> causesOfReturns) {
        return causesOfReturns.stream()
                .map(this::toDto)
                .toList();
    }

}
