package com.example.internet_shop.causesofreturns;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CauseOfReturnRepository extends JpaRepository<CauseOfReturn, Long> {
}
