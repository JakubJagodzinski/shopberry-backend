package com.example.shopberry.domain.employeetypes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeTypeRepository extends JpaRepository<EmployeeType, Long> {

    boolean existsByEmployeeTypeName(String employeeTypeName);

    EmployeeType findByEmployeeTypeName(String employeeTypeName);

}
