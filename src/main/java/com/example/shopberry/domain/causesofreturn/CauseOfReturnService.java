package com.example.shopberry.domain.causesofreturn;

import com.example.shopberry.domain.causesofreturn.dto.CauseOfReturnDtoMapper;
import com.example.shopberry.domain.causesofreturn.dto.CauseOfReturnResponseDto;
import com.example.shopberry.domain.causesofreturn.dto.CreateCauseOfReturnRequestDto;
import com.example.shopberry.domain.causesofreturn.dto.UpdateCauseOfReturnRequestDto;
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

    private final String CAUSE_OF_RETURN_NOT_FOUND_MESSAGE = "Cause of return not found";
    private final String CAUSE_OF_RETURN_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE = "Cause of return with that name already exists";
    private final String CAUSE_CANNOT_BE_NULL_MESSAGE = "Cause cannot be null";
    private final String CAUSE_CANNOT_BE_EMPTY_MESSAGE = "Cause cannot be empty";

    public List<CauseOfReturnResponseDto> getCausesOfReturn() {
        return causeOfReturnDtoMapper.toDtoList(causeOfReturnRepository.findAll());
    }

    @Transactional
    public CauseOfReturnResponseDto getCauseOfReturnById(Long id) throws EntityNotFoundException {
        CauseOfReturn causeOfReturn = causeOfReturnRepository.findById(id).orElse(null);

        if (causeOfReturn == null) {
            throw new EntityNotFoundException(CAUSE_OF_RETURN_NOT_FOUND_MESSAGE);
        }

        return causeOfReturnDtoMapper.toDto(causeOfReturn);
    }

    @Transactional
    public CauseOfReturnResponseDto createCauseOfReturn(CreateCauseOfReturnRequestDto createCauseOfReturnRequestDto) throws IllegalArgumentException {
        CauseOfReturn causeOfReturn = new CauseOfReturn();

        if (causeOfReturnRepository.existsByCause(createCauseOfReturnRequestDto.getCause())) {
            throw new IllegalArgumentException(CAUSE_OF_RETURN_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
        }

        if (createCauseOfReturnRequestDto.getCause() == null) {
            throw new IllegalArgumentException(CAUSE_CANNOT_BE_NULL_MESSAGE);
        }

        if (createCauseOfReturnRequestDto.getCause().isEmpty()) {
            throw new IllegalArgumentException(CAUSE_CANNOT_BE_EMPTY_MESSAGE);
        }

        causeOfReturn.setCause(createCauseOfReturnRequestDto.getCause());

        return causeOfReturnDtoMapper.toDto(causeOfReturnRepository.save(causeOfReturn));
    }

    @Transactional
    public CauseOfReturnResponseDto updateCauseOfReturnById(Long id, UpdateCauseOfReturnRequestDto updateCauseOfReturnRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        CauseOfReturn causeOfReturn = causeOfReturnRepository.findById(id).orElse(null);

        if (causeOfReturn == null) {
            throw new EntityNotFoundException(CAUSE_OF_RETURN_NOT_FOUND_MESSAGE);
        }

        if (updateCauseOfReturnRequestDto.getCause() != null) {
            CauseOfReturn otherCauseOfReturn = causeOfReturnRepository.findByCause(updateCauseOfReturnRequestDto.getCause());

            if (otherCauseOfReturn != null && !otherCauseOfReturn.getCauseOfReturnId().equals(id)) {
                throw new IllegalArgumentException(CAUSE_OF_RETURN_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
            }

            if (updateCauseOfReturnRequestDto.getCause().isEmpty()) {
                throw new IllegalArgumentException(CAUSE_CANNOT_BE_EMPTY_MESSAGE);
            }

            causeOfReturn.setCause(updateCauseOfReturnRequestDto.getCause());
        }

        return causeOfReturnDtoMapper.toDto(causeOfReturnRepository.save(causeOfReturn));
    }

    @Transactional
    public void deleteCauseOfReturnById(Long id) throws EntityNotFoundException {
        if (!causeOfReturnRepository.existsById(id)) {
            throw new EntityNotFoundException(CAUSE_OF_RETURN_NOT_FOUND_MESSAGE);
        }

        causeOfReturnRepository.deleteById(id);
    }

}
