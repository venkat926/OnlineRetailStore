package org.kvn.onlineRetailStore3.repository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.kvn.onlineRetailStore3.entity.Product;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepo implements ProductRepoInterface{
    private Connection connection;
    public ProductRepo(Connection connection) {
        this.connection = connection;
    }

    @PostConstruct
    private void createProductTableInDB() {
        System.out.println("Creating Product table if not already present in DB");
        String sql = """
                CREATE TABLE IF NOT EXISTS Product (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      stock INT NOT NULL,
                      price DECIMAL(10, 2) NOT NULL
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
                System.out.println("closing Product DB connection in @PreDestroy Method");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Add product to DB
    public int addProduct(Product product) {
        System.out.println("Adding a product details in DB");
        String sql = """
            INSERT INTO Product(name, stock, price) VALUES(?, ?, ?);
            """;
        int rowsAffected = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getStock());
            preparedStatement.setDouble(3, product.getPrice());

            rowsAffected = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
    // Delete a product  from DB
    public int deleteProduct(int id) {
        System.out.println("Deleting a product details from DB");
        String sql = """
            DELETE FROM Product WHERE id = ?;
            """;
        int rowsAffected = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            rowsAffected = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
    // update stock of a product
    public int updateProduct( int id, int stock) {
        System.out.println("Updating a product details in DB");
        String sql = """
            UPDATE Product SET stock = stock + ? WHERE id = ?;
            """;
        int rowsAffected = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, stock);
            preparedStatement.setInt(2, id);
            rowsAffected = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
    // get all products
    public List<Product> getAllProducts() {
        System.out.println("Getting all product details from DB");
        String sql = """
            SELECT * FROM Product;
            """;
        List<Product> productList = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Product product = Product.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .stock(resultSet.getInt(3))
                        .price(resultSet.getDouble(4))
                        .build();
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }
    // get product with id
    public Product getProduct(int id) {
        System.out.println("getting specific product details from DB");
        String sql = """
            SELECT * FROM Product WHERE id = ?;
            """;
        Product product = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    product = Product.builder()
                            .id(resultSet.getInt(1))
                            .name(resultSet.getString(2))
                            .stock(resultSet.getInt(3))
                            .price(resultSet.getDouble(4))
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }
}
