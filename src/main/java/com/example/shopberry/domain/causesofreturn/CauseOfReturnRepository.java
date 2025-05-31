package com.example.shopberry.domain.causesofreturn;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CauseOfReturnRepository extends JpaRepository<CauseOfReturn, Long> {

    boolean existsByCause(String cause);

    CauseOfReturn findByCause(String cause);

}
