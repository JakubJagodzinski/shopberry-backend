package com.example.shopberry.domain.producers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    boolean existsByProducerName(String producerName);

    Optional<Producer> findByProducerName(String producerName);

}
