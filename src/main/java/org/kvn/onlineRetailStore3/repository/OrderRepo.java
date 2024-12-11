package org.kvn.onlineRetailStore3.repository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.kvn.onlineRetailStore3.entity.Order;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepo implements OrderRepoInterface{
    private Connection connection;

    public OrderRepo(Connection connection) {
        this.connection = connection;
    }

    @PostConstruct
    private void createOrderTableInDB() {
        System.out.println("Creating Order table if not already present in DB");
        String sql = """
                CREATE TABLE IF NOT EXISTS Orders (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    customerId INT NOT NULL,
                    productID INT NOT NULL,
                    quantity INT NOT NULL,
                    orderValue DECIMAL(10, 2) NOT NULL,
                    FOREIGN KEY (customerId) REFERENCES Customer(id),
                    FOREIGN KEY (productID) REFERENCES Product(id)
                );
                """;
        try(Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @PreDestroy
    // indicates a method that should be executed before the bean is destroyed. This is used for cleanup logic.
    private void preDestroyMethod() {
        try {
            if (connection!=null && !connection.isClosed()) {
                connection.close();
                System.out.println("closing Order DB connection in @PreDestroy Method");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private  int getOrderId() {
        String sql = "SELECT MAX(ID) FROM ORDERS;";
        int orderId = 1;
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                orderId = Math.max(orderId, resultSet.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderId+1;
    }
    // Add an order to the DB
    public  int addOrder(Order order) {
        System.out.println("Adding order details in DB");
        int orderId = getOrderId();
        int rowsAffected = 0;
        String sql = """
                INSERT INTO ORDERS(id, customerId, productId, quantity, orderValue)
                VALUES(?, ?, ?, ?, ?);
                """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, order.getCustomerId());
            preparedStatement.setInt(3, order.getProductId());
            preparedStatement.setInt(4, order.getQuantity());
            preparedStatement.setDouble(5, order.getOrderValue());

            rowsAffected = preparedStatement.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        }
        return rowsAffected;
    }

    // get order
    public Order getOrder(int orderId) {
        System.out.println("getting a order details from DB");
        String sql = """
                SELECT * FROM ORDERS WHERE ID = ? ;
                """;
        Order order = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    order = Order.builder()
                            .id(resultSet.getInt(1))
                            .customerId(resultSet.getInt(2))
                            .productId(resultSet.getInt(3))
                            .quantity(resultSet.getInt(4))
                            .orderValue(resultSet.getDouble(5))
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    // get all orders
    public List<Order> getAllOrders() {
        System.out.println("getting all orders details from DB");
        String sql = """
                SELECT * FROM ORDERS;
                """;
        List<Order> orderList = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Order order = Order.builder()
                        .id(resultSet.getInt(1))
                        .customerId(resultSet.getInt(2))
                        .productId(resultSet.getInt(3))
                        .quantity(resultSet.getInt(4))
                        .orderValue(resultSet.getDouble(5))
                        .build();
                orderList.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderList;
    }
}
