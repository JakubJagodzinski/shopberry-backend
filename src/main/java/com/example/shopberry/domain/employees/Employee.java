package com.example.shopberry.domain.employees;

import com.example.shopberry.domain.employeetypes.EmployeeType;
import com.example.shopberry.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employees")
@PrimaryKeyJoinColumn(name = "employee_id")
public class Employee extends User {

    @ManyToOne
    @JoinColumn(name = "employee_type_id", nullable = false)
    private EmployeeType employeeType;

}
