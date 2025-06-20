package com.example.shopberry.domain.employeetypes;

import com.example.shopberry.common.constants.messages.EmployeeTypeMessages;
import com.example.shopberry.domain.employeetypes.dto.CreateEmployeeTypeRequestDto;
import com.example.shopberry.domain.employeetypes.dto.EmployeeTypeDtoMapper;
import com.example.shopberry.domain.employeetypes.dto.EmployeeTypeResponseDto;
import com.example.shopberry.domain.employeetypes.dto.UpdateEmployeeTypeRequestDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeTypeService {

    private final EmployeeTypeRepository employeeTypeRepository;

    private final EmployeeTypeDtoMapper employeeTypeDtoMapper;

    public List<EmployeeTypeResponseDto> getAllEmployeeTypes() {
        return employeeTypeDtoMapper.toDtoList(employeeTypeRepository.findAll());
    }

    @Transactional
    public EmployeeTypeResponseDto getEmployeeTypeById(Long employeeTypeId) throws EntityNotFoundException {
        EmployeeType employeeType = employeeTypeRepository.findById(employeeTypeId).orElse(null);

        if (employeeType == null) {
            throw new EntityNotFoundException(EmployeeTypeMessages.EMPLOYEE_TYPE_NOT_FOUND);
        }

        return employeeTypeDtoMapper.toDto(employeeType);
    }

    @Transactional
    public EmployeeTypeResponseDto createEmployeeType(CreateEmployeeTypeRequestDto createEmployeeTypeRequestDto) throws EntityExistsException, IllegalArgumentException {
        if (employeeTypeRepository.existsByEmployeeTypeName(createEmployeeTypeRequestDto.getEmployeeTypeName())) {
            throw new EntityExistsException(EmployeeTypeMessages.EMPLOYEE_TYPE_WITH_THAT_NAME_ALREADY_EXISTS);
        }

        if (createEmployeeTypeRequestDto.getEmployeeTypeName() == null) {
            throw new IllegalArgumentException(EmployeeTypeMessages.EMPLOYEE_TYPE_NAME_CANNOT_BE_NULL);
        }

        if (createEmployeeTypeRequestDto.getEmployeeTypeName().isEmpty()) {
            throw new IllegalArgumentException(EmployeeTypeMessages.EMPLOYEE_TYPE_NAME_CANNOT_BE_EMPTY);
        }

        EmployeeType employeeType = new EmployeeType();

        employeeType.setEmployeeTypeName(createEmployeeTypeRequestDto.getEmployeeTypeName());

        return employeeTypeDtoMapper.toDto(employeeTypeRepository.save(employeeType));
    }

    @Transactional
    public EmployeeTypeResponseDto updateEmployeeTypeById(Long employeeTypeId, UpdateEmployeeTypeRequestDto updateEmployeeTypeRequestDto) throws EntityExistsException, EntityNotFoundException, IllegalArgumentException {
        EmployeeType employeeType = employeeTypeRepository.findById(employeeTypeId).orElse(null);

        if (employeeType == null) {
            throw new EntityNotFoundException(EmployeeTypeMessages.EMPLOYEE_TYPE_NOT_FOUND);
        }

        if (updateEmployeeTypeRequestDto.getEmployeeTypeName() != null) {
            EmployeeType otherEmployeeType = employeeTypeRepository.findByEmployeeTypeName(updateEmployeeTypeRequestDto.getEmployeeTypeName()).orElse(null);

            if (otherEmployeeType != null && !otherEmployeeType.getEmployeeTypeId().equals(employeeTypeId)) {
                throw new EntityExistsException(EmployeeTypeMessages.EMPLOYEE_TYPE_WITH_THAT_NAME_ALREADY_EXISTS);
            }

            if (updateEmployeeTypeRequestDto.getEmployeeTypeName().isEmpty()) {
                throw new IllegalArgumentException(EmployeeTypeMessages.EMPLOYEE_TYPE_NAME_CANNOT_BE_EMPTY);
            }

            employeeType.setEmployeeTypeName(updateEmployeeTypeRequestDto.getEmployeeTypeName());
        }

        return employeeTypeDtoMapper.toDto(employeeTypeRepository.save(employeeType));
    }

    @Transactional
    public void deleteEmployeeTypeById(Long employeeTypeId) throws EntityNotFoundException {
        if (!employeeTypeRepository.existsById(employeeTypeId)) {
            throw new EntityNotFoundException(EmployeeTypeMessages.EMPLOYEE_TYPE_NOT_FOUND);
        }

        employeeTypeRepository.deleteById(employeeTypeId);
    }

}
