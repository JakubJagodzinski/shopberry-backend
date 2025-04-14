package com.example.internet_shop.employeetypes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "employee_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class EmployeeType {

    @Id
    @Column(name = "employee_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeTypeId;

    @Column(name = "employee_type_name", unique = true, nullable = false)
    private String employeeTypeName;

}
