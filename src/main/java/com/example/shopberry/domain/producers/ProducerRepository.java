package com.example.shopberry.domain.producers;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    boolean existsByProducerName(String producerName);

    Producer findByProducerName(String producerName);

}
