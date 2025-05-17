package com.example.shopberry.domain.productreturns;

import com.example.shopberry.domain.causesofreturn.CauseOfReturn;
import com.example.shopberry.domain.orders.Order;
import com.example.shopberry.domain.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "product_returns")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_return_id")
    private Long productReturnId;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cause_of_return_id", referencedColumnName = "cause_of_return_id", nullable = false)
    private CauseOfReturn causeOfReturn;

}
