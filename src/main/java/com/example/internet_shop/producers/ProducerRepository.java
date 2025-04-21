package com.example.internet_shop.producers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

    boolean existsByProducerName(String producerName);

    Producer findByProducerName(String producerName);

}
