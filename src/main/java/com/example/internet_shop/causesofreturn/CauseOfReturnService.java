package com.example.internet_shop.causesofreturn;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CauseOfReturnService {

    private final CauseOfReturnRepository causeOfReturnRepository;

    private final CauseOfReturnDtoMapper causeOfReturnDtoMapper;

    private final String CAUSE_OF_RETURN_NOT_FOUND_MESSAGE = "Cause of return not found";
    private final String CAUSE_OF_RETURN_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE = "Cause of return with that name already exists";
    private final String CAUSE_CANNOT_BE_NULL_MESSAGE = "Cause cannot be null";
    private final String CAUSE_CANNOT_BE_EMPTY_MESSAGE = "Cause cannot be empty";

    public CauseOfReturnService(CauseOfReturnRepository causeOfReturnRepository, CauseOfReturnDtoMapper causeOfReturnDtoMapper) {
        this.causeOfReturnRepository = causeOfReturnRepository;
        this.causeOfReturnDtoMapper = causeOfReturnDtoMapper;
    }

    public List<CauseOfReturnDto> getCausesOfReturn() {
        return causeOfReturnDtoMapper.toDtoList(causeOfReturnRepository.findAll());
    }

    @Transactional
    public CauseOfReturnDto getCauseOfReturnById(Long id) throws EntityNotFoundException {
        if (!causeOfReturnRepository.existsById(id)) {
            throw new EntityNotFoundException(CAUSE_OF_RETURN_NOT_FOUND_MESSAGE);
        }

        return causeOfReturnDtoMapper.toDto(causeOfReturnRepository.getReferenceById(id));
    }

    @Transactional
    public CauseOfReturnDto createCauseOfReturn(CreateCauseOfReturnDto createCauseOfReturnDto) throws IllegalStateException {
        CauseOfReturn causeOfReturn = new CauseOfReturn();

        if (causeOfReturnRepository.existsByCause(createCauseOfReturnDto.getCause())) {
            throw new IllegalStateException(CAUSE_OF_RETURN_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
        }

        if (createCauseOfReturnDto.getCause() == null) {
            throw new IllegalStateException(CAUSE_CANNOT_BE_NULL_MESSAGE);
        }

        if (createCauseOfReturnDto.getCause().isEmpty()) {
            throw new IllegalStateException(CAUSE_CANNOT_BE_EMPTY_MESSAGE);
        }

        causeOfReturn.setCause(createCauseOfReturnDto.getCause());

        return causeOfReturnDtoMapper.toDto(causeOfReturnRepository.save(causeOfReturn));
    }

    @Transactional
    public CauseOfReturnDto updateCauseOfReturnById(Long id, UpdateCauseOfReturnDto updateCauseOfReturnDto) throws EntityNotFoundException, IllegalStateException {
        if (!causeOfReturnRepository.existsById(id)) {
            throw new EntityNotFoundException(CAUSE_OF_RETURN_NOT_FOUND_MESSAGE);
        }

        CauseOfReturn causeOfReturn = causeOfReturnRepository.getReferenceById(id);

        if (updateCauseOfReturnDto.getCause() != null) {
            CauseOfReturn otherCauseOfReturn = causeOfReturnRepository.findByCause(updateCauseOfReturnDto.getCause());

            if (otherCauseOfReturn != null && !otherCauseOfReturn.getCauseOfReturnId().equals(id)) {
                throw new IllegalStateException(CAUSE_OF_RETURN_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
            }

            if (updateCauseOfReturnDto.getCause().isEmpty()) {
                throw new IllegalStateException(CAUSE_CANNOT_BE_EMPTY_MESSAGE);
            }

            causeOfReturn.setCause(updateCauseOfReturnDto.getCause());
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
