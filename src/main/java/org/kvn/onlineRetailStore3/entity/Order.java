package org.kvn.onlineRetailStore3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Order {
    private int id;
    private int customerId;
    private int productId;
    private int quantity;
    private double orderValue;
}
