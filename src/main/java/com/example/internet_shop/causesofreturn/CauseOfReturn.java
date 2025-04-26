package com.example.internet_shop.causesofreturn;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "causes_of_returns")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CauseOfReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cause_of_return_id")
    private Long causeOfReturnId;

    @Column(nullable = false, unique = true, length = 100)
    private String cause;

}
