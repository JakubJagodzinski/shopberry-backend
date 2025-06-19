package com.example.shopberry.domain.employeetypes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeTypeRepository extends JpaRepository<EmployeeType, Long> {

    boolean existsByEmployeeTypeName(String employeeTypeName);

    Optional<EmployeeType> findByEmployeeTypeName(String employeeTypeName);

}
