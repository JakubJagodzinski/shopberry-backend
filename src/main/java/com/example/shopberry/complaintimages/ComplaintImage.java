package com.example.shopberry.complaintimages;

import com.example.shopberry.complaints.Complaint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "complaint_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", referencedColumnName = "complaint_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Complaint complaint;

    @Lob
    private byte[] image;

}
