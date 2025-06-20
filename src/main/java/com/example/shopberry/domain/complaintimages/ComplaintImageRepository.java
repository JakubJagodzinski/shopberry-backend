package com.example.shopberry.domain.complaintimages;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintImageRepository extends JpaRepository<ComplaintImage, Long> {

    List<ComplaintImage> findAllByComplaint_ComplaintId(Long complaintId);

}
