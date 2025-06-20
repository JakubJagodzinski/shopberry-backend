package com.example.shopberry.domain.customers;

import com.example.shopberry.domain.customeraddresses.CustomerAddress;
import com.example.shopberry.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_customers_user"))
public class Customer extends User {

    @Column(name = "is_company")
    private Boolean isCompany = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_address_id", foreignKey = @ForeignKey(name = "fk_customers_address"))
    private CustomerAddress mainAddress;

}
