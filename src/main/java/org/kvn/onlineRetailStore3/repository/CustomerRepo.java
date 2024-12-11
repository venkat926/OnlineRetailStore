package org.kvn.onlineRetailStore3.repository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.kvn.onlineRetailStore3.entity.Customer;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepo implements CustomerRepoInterface{
    private Connection connection;
    public CustomerRepo(Connection connection) {
        this.connection = connection;

    }

    @PostConstruct
    private void createCustomerTableInDB() {
        System.out.println("Creating Customer table if not already present in DB");
        String sql = """
                CREATE TABLE IF NOT EXISTS CUSTOMER (
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(50) NOT NULL,
                address VARCHAR(100),
                phoneNo VARCHAR(15)
                );
                """;
        try(Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy // indicates a method that should be executed before the bean is destroyed. This is used for cleanup logic.
    private void preDestroyMethod() {
        try {
            if (connection!=null && !connection.isClosed()) {
                connection.close();
                System.out.println("closing Customer DB connection in @PreDestroy Method");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // return rowsAffected as response to API
    public int addCustomer(Customer customer)  {
        System.out.println("Adding Customer to DB");
        String sql = """
                INSERT INTO CUSTOMER(id, name, address, phoneNo) VALUES(?, ?, ?, ?);
                """;
        int rowsAffected = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setLong(4, customer.getPhoneNo());
            rowsAffected =  preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
    public int deleteCustomer(int id) {
        System.out.println("Deleting Customer from DB");
        String sql = """
                DELETE FROM CUSTOMER WHERE ID = ?;
                """;
        int rowsAffected = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            rowsAffected = preparedStatement.executeUpdate();
        } catch (DataAccessException e) {
           throw e;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowsAffected;
    }
    public int updateCustomer(int id, String address) {
        System.out.println("Updating Customer Details in DB");
        String sql = """
                UPDATE CUSTOMER SET ADDRESS = ? WHERE ID = ?
                """;
        int rowsAffected = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, address);
            preparedStatement.setInt(2, id);
            rowsAffected = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
    public List<Customer> getAllCustomers() {
        System.out.println("Getting all Customer details from DB");
        String sql = """
                SELECT * FROM CUSTOMER;
                """;
        ResultSet resultSet = null;
        List<Customer> customerList = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Customer customer = Customer.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .address(resultSet.getString(3))
                        .phoneNo(resultSet.getLong(4))
                        .build();
                customerList.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerList;
    }
    public Customer getCustomer(int id) {
        System.out.println("Getting specific Customer details from DB");
        String sql = """
                SELECT * FROM CUSTOMER WHERE ID = ?;
                """;
        ResultSet resultSet = null;
        Customer customer = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = Customer.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .address(resultSet.getString(3))
                        .phoneNo(resultSet.getLong(4))
                        .build();
            } else {
                customer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }
}
