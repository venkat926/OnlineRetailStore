package org.kvn.onlineRetailStore3.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "customerId", nullable = false)
    private CustomerEntity customerEntity;

    @ManyToOne()
    @JoinColumn(name = "productId", nullable = false)
    private ProductEntity productEntity;

    private int quantity;
    private double orderValue;
}
