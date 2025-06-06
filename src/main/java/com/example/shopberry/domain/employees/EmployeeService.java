package com.example.shopberry.domain.employees;

import com.example.shopberry.auth.dto.RegisterRequestDto;
import com.example.shopberry.domain.employees.dto.EmployeeDtoMapper;
import com.example.shopberry.domain.employees.dto.EmployeeResponseDto;
import com.example.shopberry.domain.employees.dto.UpdateEmployeeRequestDto;
import com.example.shopberry.domain.employeetypes.EmployeeType;
import com.example.shopberry.domain.employeetypes.EmployeeTypeRepository;
import com.example.shopberry.user.Role;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeTypeRepository employeeTypeRepository;

    private final EmployeeDtoMapper employeeDtoMapper;

    private final PasswordEncoder passwordEncoder;

    private static final String EMPLOYEE_NOT_FOUND_MESSAGE = "Employee not found";
    private static final String EMPLOYEE_TYPE_NOT_FOUND_MESSAGE = "Employee type not found";

    public List<EmployeeResponseDto> getEmployees() {
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
    public Employee register(RegisterRequestDto registerRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        EmployeeType employeeType = employeeTypeRepository.findById(registerRequestDto.getEmployeeTypeId()).orElse(null);

        if (employeeType == null) {
            throw new EntityNotFoundException(EMPLOYEE_TYPE_NOT_FOUND_MESSAGE);
        }

        Employee employee = new Employee();

        employee.setFirstName(registerRequestDto.getFirstname());
        employee.setLastName(registerRequestDto.getLastname());
        employee.setEmail(registerRequestDto.getEmail());
        employee.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        employee.setRole(Role.valueOf(registerRequestDto.getRole()));
        employee.setEmployeeType(employeeType);

        return employeeRepository.save(employee);
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
