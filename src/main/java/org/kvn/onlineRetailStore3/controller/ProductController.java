package org.kvn.onlineRetailStore3.controller;

import org.kvn.onlineRetailStore3.entity.Product;
import org.kvn.onlineRetailStore3.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
@ResponseBody
public class ProductController {
    @Autowired
    private ProductRepo productRepo;

    @GetMapping
    public List<Product> getAllProducts() {
        System.out.println("Get All Products  API is called");
        return productRepo.getAllProducts();
    }
    @GetMapping("/{id}")
    public Product getProductByID(@PathVariable Integer id) {
        System.out.println("Get Product By ID API is called");
        return productRepo.getProduct(id);
    }

    @PostMapping
    public String postProductInfo(@RequestBody Product product) {
        int rowsAffected = productRepo.addProduct(product);
        return (rowsAffected > 0) ? "Product Added Successfully" : "Product Not Added";
    }

    @PutMapping
    public String putProductInfo(@RequestBody Product product) {
        int rowsAffected = productRepo.updateProduct(product.getId(), product.getStock());
        return (rowsAffected > 0) ? "Product stock Updated Successfully" : "Product with id: " + product.getId() + " not found";
    }

    @DeleteMapping
    public String deleteProductInfo(@RequestBody Product product) {
        int rowsAffected = productRepo.deleteProduct(product.getId());
        return (rowsAffected > 0) ? "Product Deleted Successfully" : "Product with id: " + product.getId() + " not found";
    }

}
