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
public class Customer {
    private int id;
    private String name;
    private String address;
    private long phoneNo;
}
