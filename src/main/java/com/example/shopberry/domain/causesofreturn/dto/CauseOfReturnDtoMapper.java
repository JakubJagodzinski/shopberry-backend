package com.example.shopberry.domain.causesofreturn.dto;

import com.example.shopberry.domain.causesofreturn.CauseOfReturn;
import com.example.shopberry.domain.causesofreturn.dto.response.CauseOfReturnResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CauseOfReturnDtoMapper {

    public CauseOfReturnResponseDto toDto(CauseOfReturn causeOfReturn) {
        if (causeOfReturn == null) {
            return null;
        }

        CauseOfReturnResponseDto causeOfReturnResponseDto = new CauseOfReturnResponseDto();

        causeOfReturnResponseDto.setCauseOfReturnId(causeOfReturn.getCauseOfReturnId());
        causeOfReturnResponseDto.setCause(causeOfReturn.getCause());

        return causeOfReturnResponseDto;
    }

    public List<CauseOfReturnResponseDto> toDtoList(List<CauseOfReturn> causeOfReturnList) {
        return causeOfReturnList.stream()
                .map(this::toDto)
                .toList();
    }

}
