package com.example.shopberry.domain.causesofreturn;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CauseOfReturnRepository extends JpaRepository<CauseOfReturn, Long> {

    boolean existsByCause(String cause);

    Optional<CauseOfReturn> findByCause(String cause);

}
