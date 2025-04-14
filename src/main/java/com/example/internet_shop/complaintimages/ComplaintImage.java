package com.example.internet_shop.complaintimages;

import com.example.internet_shop.complaints.Complaint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "complaint_images")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", referencedColumnName = "complaint_id", nullable = false)
    private Complaint complaint;

    private Byte[] image;

}
