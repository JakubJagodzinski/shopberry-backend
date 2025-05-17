package com.example.shopberry.employeetypes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_type_id")
    private Long employeeTypeId;

    @Column(name = "employee_type_name", unique = true, nullable = false, length = 100)
    private String employeeTypeName;

}
