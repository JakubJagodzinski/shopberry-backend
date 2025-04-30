package com.example.internet_shop.employees;

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

    public List<EmployeeDto> getEmployees() {
        return employeeDtoMapper.toDtoList(employeeRepository.findAll());
    }

    @Transactional
    public EmployeeDto getEmployeeById(Long id) throws EntityNotFoundException {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException(EMPLOYEE_NOT_FOUND_MESSAGE);
        }

        return employeeDtoMapper.toDto(employeeRepository.getReferenceById(id));
    }

    @Transactional
    public EmployeeDto createEmployee(CreateEmployeeDto createEmployeeDto) throws IllegalArgumentException {
        Employee employee = new Employee();

        if (employeeRepository.existsByEmail(createEmployeeDto.getEmail())) {
            throw new IllegalArgumentException(EMPLOYEE_WITH_THAT_EMAIL_ALREADY_EXISTS);
        }

        employee.setEmail(createEmployeeDto.getEmail());
        employee.setFirstName(createEmployeeDto.getFirstName());
        employee.setLastName(createEmployeeDto.getLastName());
        employee.setPassword(createEmployeeDto.getPassword());

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
