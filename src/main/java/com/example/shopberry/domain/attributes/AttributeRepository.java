package com.example.shopberry.domain.attributes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    boolean existsByAttributeName(String attributeName);

    Attribute findByAttributeName(String attributeName);

}
