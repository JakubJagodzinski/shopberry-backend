package com.example.internet_shop.producers;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "producers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Producer {

    @Id
    @Column(name = "producer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long producerId;

    @Column(name = "producer_name", unique = true, nullable = false)
    private String producerName;

}
