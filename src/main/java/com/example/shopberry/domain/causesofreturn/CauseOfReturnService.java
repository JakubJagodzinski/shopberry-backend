package com.example.shopberry.domain.causesofreturn;

import com.example.shopberry.common.constants.messages.CauseOfReturnMessages;
import com.example.shopberry.domain.causesofreturn.dto.CauseOfReturnDtoMapper;
import com.example.shopberry.domain.causesofreturn.dto.CauseOfReturnResponseDto;
import com.example.shopberry.domain.causesofreturn.dto.CreateCauseOfReturnRequestDto;
import com.example.shopberry.domain.causesofreturn.dto.UpdateCauseOfReturnRequestDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CauseOfReturnService {

    private final CauseOfReturnRepository causeOfReturnRepository;

    private final CauseOfReturnDtoMapper causeOfReturnDtoMapper;

    public List<CauseOfReturnResponseDto> getAllCausesOfReturn() {
        return causeOfReturnDtoMapper.toDtoList(causeOfReturnRepository.findAll());
    }

    @Transactional
    public CauseOfReturnResponseDto getCauseOfReturnById(Long causeOfReturnId) throws EntityNotFoundException {
        CauseOfReturn causeOfReturn = causeOfReturnRepository.findById(causeOfReturnId).orElse(null);

        if (causeOfReturn == null) {
            throw new EntityNotFoundException(CauseOfReturnMessages.CAUSE_OF_RETURN_NOT_FOUND);
        }

        return causeOfReturnDtoMapper.toDto(causeOfReturn);
    }

    @Transactional
    public CauseOfReturnResponseDto createCauseOfReturn(CreateCauseOfReturnRequestDto createCauseOfReturnRequestDto) throws EntityExistsException, IllegalArgumentException {
        CauseOfReturn causeOfReturn = new CauseOfReturn();

        if (causeOfReturnRepository.existsByCause(createCauseOfReturnRequestDto.getCause())) {
            throw new EntityExistsException(CauseOfReturnMessages.CAUSE_OF_RETURN_WITH_THAT_NAME_ALREADY_EXISTS);
        }

        if (createCauseOfReturnRequestDto.getCause() == null) {
            throw new IllegalArgumentException(CauseOfReturnMessages.CAUSE_CANNOT_BE_NULL);
        }

        if (createCauseOfReturnRequestDto.getCause().isEmpty()) {
            throw new IllegalArgumentException(CauseOfReturnMessages.CAUSE_CANNOT_BE_EMPTY);
        }

        causeOfReturn.setCause(createCauseOfReturnRequestDto.getCause());

        return causeOfReturnDtoMapper.toDto(causeOfReturnRepository.save(causeOfReturn));
    }

    @Transactional
    public CauseOfReturnResponseDto updateCauseOfReturnById(Long causeOfReturnId, UpdateCauseOfReturnRequestDto updateCauseOfReturnRequestDto) throws EntityNotFoundException, EntityExistsException, IllegalArgumentException {
        CauseOfReturn causeOfReturn = causeOfReturnRepository.findById(causeOfReturnId).orElse(null);

        if (causeOfReturn == null) {
            throw new EntityNotFoundException(CauseOfReturnMessages.CAUSE_OF_RETURN_NOT_FOUND);
        }

        if (updateCauseOfReturnRequestDto.getCause() != null) {
            CauseOfReturn otherCauseOfReturn = causeOfReturnRepository.findByCause(updateCauseOfReturnRequestDto.getCause()).orElse(null);

            if (otherCauseOfReturn != null && !otherCauseOfReturn.getCauseOfReturnId().equals(causeOfReturnId)) {
                throw new EntityExistsException(CauseOfReturnMessages.CAUSE_OF_RETURN_WITH_THAT_NAME_ALREADY_EXISTS);
            }

            if (updateCauseOfReturnRequestDto.getCause().isEmpty()) {
                throw new IllegalArgumentException(CauseOfReturnMessages.CAUSE_CANNOT_BE_EMPTY);
            }

            causeOfReturn.setCause(updateCauseOfReturnRequestDto.getCause());
        }

        return causeOfReturnDtoMapper.toDto(causeOfReturnRepository.save(causeOfReturn));
    }

    @Transactional
    public void deleteCauseOfReturnById(Long causeOfReturnId) throws EntityNotFoundException {
        if (!causeOfReturnRepository.existsById(causeOfReturnId)) {
            throw new EntityNotFoundException(CauseOfReturnMessages.CAUSE_OF_RETURN_NOT_FOUND);
        }

        causeOfReturnRepository.deleteById(causeOfReturnId);
    }

}
