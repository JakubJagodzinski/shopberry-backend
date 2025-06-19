package com.example.shopberry.domain.employees;

import com.example.shopberry.domain.employees.dto.EmployeeDtoMapper;
import com.example.shopberry.domain.employees.dto.EmployeeResponseDto;
import com.example.shopberry.domain.employees.dto.UpdateEmployeeRequestDto;
import com.example.shopberry.domain.employeetypes.EmployeeType;
import com.example.shopberry.domain.employeetypes.EmployeeTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeTypeRepository employeeTypeRepository;

    private final EmployeeDtoMapper employeeDtoMapper;

    private static final String EMPLOYEE_NOT_FOUND_MESSAGE = "Employee not found";
    private static final String EMPLOYEE_TYPE_NOT_FOUND_MESSAGE = "Employee type not found";

    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeDtoMapper.toDtoList(employeeRepository.findAll());
    }

    @Transactional
    public EmployeeResponseDto getEmployeeById(Long id) throws EntityNotFoundException {
        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            throw new EntityNotFoundException(EMPLOYEE_NOT_FOUND_MESSAGE);
        }

        return employeeDtoMapper.toDto(employee);
    }

    @Transactional
    public EmployeeResponseDto updateEmployeeById(Long id, UpdateEmployeeRequestDto updateEmployeeRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            throw new EntityNotFoundException(EMPLOYEE_NOT_FOUND_MESSAGE);
        }

        if (updateEmployeeRequestDto.getEmployeeTypeId() != null) {
            EmployeeType employeeType = employeeTypeRepository.findById(updateEmployeeRequestDto.getEmployeeTypeId()).orElse(null);

            if (employeeType == null) {
                throw new EntityNotFoundException(EMPLOYEE_TYPE_NOT_FOUND_MESSAGE);
            }

            employee.setEmployeeType(employeeType);
        }

        return employeeDtoMapper.toDto(employeeRepository.save(employee));
    }

    @Transactional
    public void deleteEmployeeById(Long id) throws EntityNotFoundException {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException(EMPLOYEE_NOT_FOUND_MESSAGE);
        }

        employeeRepository.deleteById(id);
    }

}
