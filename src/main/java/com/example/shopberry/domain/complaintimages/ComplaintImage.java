package com.example.shopberry.domain.complaintimages;

import com.example.shopberry.domain.complaints.Complaint;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private Long imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false, foreignKey = @ForeignKey(name = "fk_complaint_images_complaint"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Complaint complaint;

    @Lob
    private byte[] image;

}
