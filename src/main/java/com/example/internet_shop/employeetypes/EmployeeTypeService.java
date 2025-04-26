package com.example.internet_shop.employeetypes;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeTypeService {

    private final EmployeeTypeRepository employeeTypeRepository;

    private final EmployeeTypeDtoMapper employeeTypeDtoMapper;

    private final String EMPLOYEE_TYPE_NOT_FOUND_MESSAGE = "Employee type not found";
    private final String EMPLOYEE_TYPE_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE = "Employee type with that name already exists";
    private final String EMPLOYEE_TYPE_NAME_CANNOT_BE_NULL_MESSAGE = "Employee type name cannot be null";
    private final String EMPLOYEE_TYPE_NAME_CANNOT_BE_EMPTY_MESSAGE = "Employee type name cannot be empty";

    public EmployeeTypeService(EmployeeTypeRepository employeeTypeRepository, EmployeeTypeDtoMapper employeeTypeDtoMapper) {
        this.employeeTypeRepository = employeeTypeRepository;
        this.employeeTypeDtoMapper = employeeTypeDtoMapper;
    }

    public List<EmployeeTypeDto> getEmployeeTypes() {
        return employeeTypeDtoMapper.toDtoList(employeeTypeRepository.findAll());
    }

    @Transactional
    public EmployeeTypeDto getEmployeeTypeById(Long id) throws EntityNotFoundException {
        if (!employeeTypeRepository.existsById(id)) {
            throw new EntityNotFoundException(EMPLOYEE_TYPE_NOT_FOUND_MESSAGE);
        }

        return employeeTypeDtoMapper.toDto(employeeTypeRepository.getReferenceById(id));
    }

    @Transactional
    public EmployeeTypeDto createEmployeeType(CreateEmployeeTypeDto createEmployeeTypeDto) throws IllegalArgumentException {
        if (employeeTypeRepository.existsByEmployeeTypeName(createEmployeeTypeDto.getEmployeeTypeName())) {
            throw new IllegalArgumentException(EMPLOYEE_TYPE_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
        }

        if (createEmployeeTypeDto.getEmployeeTypeName() == null) {
            throw new IllegalArgumentException(EMPLOYEE_TYPE_NAME_CANNOT_BE_NULL_MESSAGE);
        }

        if (createEmployeeTypeDto.getEmployeeTypeName().isEmpty()) {
            throw new IllegalArgumentException(EMPLOYEE_TYPE_NAME_CANNOT_BE_EMPTY_MESSAGE);
        }

        EmployeeType employeeType = new EmployeeType();

        employeeType.setEmployeeTypeName(createEmployeeTypeDto.getEmployeeTypeName());

        return employeeTypeDtoMapper.toDto(employeeTypeRepository.save(employeeType));
    }

    @Transactional
    public EmployeeTypeDto updateEmployeeTypeById(Long id, UpdateEmployeeTypeDto updateEmployeeTypeDto) throws EntityNotFoundException, IllegalArgumentException {
        if (!employeeTypeRepository.existsById(id)) {
            throw new EntityNotFoundException(EMPLOYEE_TYPE_NOT_FOUND_MESSAGE);
        }

        EmployeeType employeeType = employeeTypeRepository.getReferenceById(id);

        if (updateEmployeeTypeDto.getEmployeeTypeName() != null) {
            EmployeeType otherEmployeeType = employeeTypeRepository.findByEmployeeTypeName(updateEmployeeTypeDto.getEmployeeTypeName());

            if (otherEmployeeType != null && !otherEmployeeType.getEmployeeTypeId().equals(id)) {
                throw new IllegalArgumentException(EMPLOYEE_TYPE_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
            }

            if (updateEmployeeTypeDto.getEmployeeTypeName().isEmpty()) {
                throw new IllegalArgumentException(EMPLOYEE_TYPE_NAME_CANNOT_BE_EMPTY_MESSAGE);
            }

            employeeType.setEmployeeTypeName(updateEmployeeTypeDto.getEmployeeTypeName());
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
