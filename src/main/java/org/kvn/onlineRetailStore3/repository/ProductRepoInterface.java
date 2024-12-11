package org.kvn.onlineRetailStore3.repository;

import org.kvn.onlineRetailStore3.entity.Product;

import java.util.List;

public interface ProductRepoInterface {
    public int addProduct(Product product);
    public int deleteProduct(int id);
    public int updateProduct( int id, int stock);
    public List<Product> getAllProducts();
    public Product getProduct(int id);
}
