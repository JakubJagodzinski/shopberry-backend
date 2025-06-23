package com.example.shopberry.domain.complaints;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findAllByOrder_Customer_UserId(UUID customerId);

}
