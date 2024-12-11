package org.kvn.onlineRetailStore3.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private int stock;
    private double price;
}
