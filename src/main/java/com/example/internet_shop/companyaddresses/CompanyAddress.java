package com.example.internet_shop.companyaddresses;

import com.example.internet_shop.customers.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "company_addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class CompanyAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "company_name", length = 100, nullable = false)
    private String companyName;

    @Column(length = 15, nullable = false)
    private String nip;

    @Column(length = 100, nullable = false)
    private String city;

    @Column(name = "postal_code", length = 20, nullable = false)
    private String postalCode;

    @Column(length = 100, nullable = false)
    private String street;

    @Column(name = "house_number", length = 10, nullable = false)
    private String houseNumber;

    @Column(length = 10)
    private String apartment;

    @Column(name = "phone_number", length = 15, nullable = false)
    private String phoneNumber;

}
