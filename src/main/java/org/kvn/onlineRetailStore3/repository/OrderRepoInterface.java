package org.kvn.onlineRetailStore3.repository;

import org.kvn.onlineRetailStore3.entity.Order;

import java.util.List;

public interface OrderRepoInterface {
    public  int addOrder(Order order);
    public Order getOrder(int orderId);
    public List<Order> getAllOrders();

}
