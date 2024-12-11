package org.kvn.onlineRetailStore3.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phoneNo")
    private long phoneNo;
}
