package com.example.shopberry.domain.attributes;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "attributes")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id")
    private Long attributeId;

    @Column(name = "attribute_name", nullable = false, unique = true, length = 100)
    private String attributeName;

}
