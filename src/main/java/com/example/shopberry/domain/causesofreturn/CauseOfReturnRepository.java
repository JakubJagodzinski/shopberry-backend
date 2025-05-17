package com.example.shopberry.domain.causesofreturn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CauseOfReturnRepository extends JpaRepository<CauseOfReturn, Long> {

    boolean existsByCause(String cause);

    CauseOfReturn findByCause(String cause);

}
