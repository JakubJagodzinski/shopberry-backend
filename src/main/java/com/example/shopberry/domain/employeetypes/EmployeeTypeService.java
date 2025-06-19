package com.example.shopberry.domain.employeetypes;

import com.example.shopberry.domain.employeetypes.dto.CreateEmployeeTypeRequestDto;
import com.example.shopberry.domain.employeetypes.dto.EmployeeTypeDtoMapper;
import com.example.shopberry.domain.employeetypes.dto.EmployeeTypeResponseDto;
import com.example.shopberry.domain.employeetypes.dto.UpdateEmployeeTypeRequestDto;
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

    private static final String EMPLOYEE_TYPE_NOT_FOUND_MESSAGE = "Employee type not found";
    private static final String EMPLOYEE_TYPE_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE = "Employee type with that name already exists";
    private static final String EMPLOYEE_TYPE_NAME_CANNOT_BE_NULL_MESSAGE = "Employee type name cannot be null";
    private static final String EMPLOYEE_TYPE_NAME_CANNOT_BE_EMPTY_MESSAGE = "Employee type name cannot be empty";

    public List<EmployeeTypeResponseDto> getAllEmployeeTypes() {
        return employeeTypeDtoMapper.toDtoList(employeeTypeRepository.findAll());
    }

    @Transactional
    public EmployeeTypeResponseDto getEmployeeTypeById(Long id) throws EntityNotFoundException {
        EmployeeType employeeType = employeeTypeRepository.findById(id).orElse(null);

        if (employeeType == null) {
            throw new EntityNotFoundException(EMPLOYEE_TYPE_NOT_FOUND_MESSAGE);
        }

        return employeeTypeDtoMapper.toDto(employeeType);
    }

    @Transactional
    public EmployeeTypeResponseDto createEmployeeType(CreateEmployeeTypeRequestDto createEmployeeTypeRequestDto) throws IllegalArgumentException {
        if (employeeTypeRepository.existsByEmployeeTypeName(createEmployeeTypeRequestDto.getEmployeeTypeName())) {
            throw new IllegalArgumentException(EMPLOYEE_TYPE_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
        }

        if (createEmployeeTypeRequestDto.getEmployeeTypeName() == null) {
            throw new IllegalArgumentException(EMPLOYEE_TYPE_NAME_CANNOT_BE_NULL_MESSAGE);
        }

        if (createEmployeeTypeRequestDto.getEmployeeTypeName().isEmpty()) {
            throw new IllegalArgumentException(EMPLOYEE_TYPE_NAME_CANNOT_BE_EMPTY_MESSAGE);
        }

        EmployeeType employeeType = new EmployeeType();

        employeeType.setEmployeeTypeName(createEmployeeTypeRequestDto.getEmployeeTypeName());

        return employeeTypeDtoMapper.toDto(employeeTypeRepository.save(employeeType));
    }

    @Transactional
    public EmployeeTypeResponseDto updateEmployeeTypeById(Long id, UpdateEmployeeTypeRequestDto updateEmployeeTypeRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        EmployeeType employeeType = employeeTypeRepository.findById(id).orElse(null);

        if (employeeType == null) {
            throw new EntityNotFoundException(EMPLOYEE_TYPE_NOT_FOUND_MESSAGE);
        }

        if (updateEmployeeTypeRequestDto.getEmployeeTypeName() != null) {
            EmployeeType otherEmployeeType = employeeTypeRepository.findByEmployeeTypeName(updateEmployeeTypeRequestDto.getEmployeeTypeName()).orElse(null);

            if (otherEmployeeType != null && !otherEmployeeType.getEmployeeTypeId().equals(id)) {
                throw new IllegalArgumentException(EMPLOYEE_TYPE_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
            }

            if (updateEmployeeTypeRequestDto.getEmployeeTypeName().isEmpty()) {
                throw new IllegalArgumentException(EMPLOYEE_TYPE_NAME_CANNOT_BE_EMPTY_MESSAGE);
            }

            employeeType.setEmployeeTypeName(updateEmployeeTypeRequestDto.getEmployeeTypeName());
        }

        return employeeTypeDtoMapper.toDto(employeeTypeRepository.save(employeeType));
    }

    @Transactional
    public void deleteEmployeeTypeById(Long id) throws EntityNotFoundException {
        if (!employeeTypeRepository.existsById(id)) {
            throw new EntityNotFoundException(EMPLOYEE_TYPE_NOT_FOUND_MESSAGE);
        }

        employeeTypeRepository.deleteById(id);
    }

}
