package com.example.shopberry.domain.attributes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    boolean existsByAttributeName(String attributeName);

    Optional<Attribute> findByAttributeName(String attributeName);

}
