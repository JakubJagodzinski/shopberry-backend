package com.example.internet_shop.employees;

import com.example.internet_shop.employees.dto.CreateEmployeeRequestDto;
import com.example.internet_shop.employees.dto.EmployeeDtoMapper;
import com.example.internet_shop.employees.dto.EmployeeResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeDtoMapper employeeDtoMapper;

    private final String EMPLOYEE_NOT_FOUND_MESSAGE = "Employee not found";
    private final String EMPLOYEE_WITH_THAT_EMAIL_ALREADY_EXISTS = "Employee with that email already exists";

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeDtoMapper employeeDtoMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeDtoMapper = employeeDtoMapper;
    }

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
    public EmployeeResponseDto createEmployee(CreateEmployeeRequestDto createEmployeeRequestDto) throws IllegalArgumentException {
        Employee employee = new Employee();

        if (employeeRepository.existsByEmail(createEmployeeRequestDto.getEmail())) {
            throw new IllegalArgumentException(EMPLOYEE_WITH_THAT_EMAIL_ALREADY_EXISTS);
        }

        employee.setEmail(createEmployeeRequestDto.getEmail());
        employee.setFirstName(createEmployeeRequestDto.getFirstName());
        employee.setLastName(createEmployeeRequestDto.getLastName());
        employee.setPassword(createEmployeeRequestDto.getPassword());

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
