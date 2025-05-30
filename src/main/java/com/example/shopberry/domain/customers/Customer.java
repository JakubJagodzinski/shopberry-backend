package com.example.shopberry.domain.customers;

import com.example.shopberry.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "customer_id")
public class Customer extends User {

    @Column(name = "is_company")
    private Boolean isCompany = false;

}
