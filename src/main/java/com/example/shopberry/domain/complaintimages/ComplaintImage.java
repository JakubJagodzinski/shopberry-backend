package com.example.shopberry.domain.complaintimages;

import com.example.shopberry.domain.complaints.Complaint;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "complaint_images")
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
