package com.example.shopberry.domain.employees;

import com.example.shopberry.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employees")
@PrimaryKeyJoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = "fk_employees_user"))
public class Employee extends User {

    @Column(name = "hired_at")
    private LocalDate hiredAt;

    @Column(name = "is_active")
    private Boolean isActive;

}
