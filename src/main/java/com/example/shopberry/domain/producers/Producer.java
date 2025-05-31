package com.example.shopberry.domain.producers;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "producers")
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producer_id")
    private Long producerId;

    @Column(name = "producer_name", unique = true, nullable = false, length = 100)
    private String producerName;

}
