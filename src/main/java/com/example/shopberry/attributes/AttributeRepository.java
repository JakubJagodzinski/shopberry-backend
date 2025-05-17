package com.example.shopberry.attributes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    boolean existsByAttributeName(String attributeName);

    Attribute findByAttributeName(String attributeName);

}
