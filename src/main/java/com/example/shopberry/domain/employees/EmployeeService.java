package com.example.shopberry.domain.employees;

import com.example.shopberry.common.constants.messages.EmployeeMessages;
import com.example.shopberry.domain.employees.dto.EmployeeDtoMapper;
import com.example.shopberry.domain.employees.dto.EmployeeResponseDto;
import com.example.shopberry.domain.employees.dto.UpdateEmployeeRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeDtoMapper employeeDtoMapper;

    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeDtoMapper.toDtoList(employeeRepository.findAll());
    }

    @Transactional
    public EmployeeResponseDto getEmployeeById(UUID employeeId) throws EntityNotFoundException {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (employee == null) {
            throw new EntityNotFoundException(EmployeeMessages.EMPLOYEE_NOT_FOUND);
        }

        return employeeDtoMapper.toDto(employee);
    }

    @Transactional
    public EmployeeResponseDto updateEmployeeById(UUID employeeId, UpdateEmployeeRequestDto updateEmployeeRequestDto) throws EntityNotFoundException {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (employee == null) {
            throw new EntityNotFoundException(EmployeeMessages.EMPLOYEE_NOT_FOUND);
        }

        if (updateEmployeeRequestDto.getFirstName() != null) {
            employee.setFirstName(updateEmployeeRequestDto.getFirstName());
        }

        if (updateEmployeeRequestDto.getLastName() != null) {
            employee.setLastName(updateEmployeeRequestDto.getLastName());
        }

        return employeeDtoMapper.toDto(employeeRepository.save(employee));
    }

    @Transactional
    public void deleteEmployeeById(UUID employeeId) throws EntityNotFoundException {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException(EmployeeMessages.EMPLOYEE_NOT_FOUND);
        }

        employeeRepository.deleteById(employeeId);
    }

}
