package org.kvn.onlineRetailStore3.controller;

import org.kvn.onlineRetailStore3.entity.Customer;
import org.kvn.onlineRetailStore3.entity.Order;
import org.kvn.onlineRetailStore3.entity.Product;
import org.kvn.onlineRetailStore3.repository.CustomerRepo;
import org.kvn.onlineRetailStore3.repository.OrderRepo;
import org.kvn.onlineRetailStore3.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/order")
public class OrderController {

    @Autowired private OrderRepo orderRepo;
    @Autowired private CustomerRepo customerRepo;
    @Autowired private ProductRepo productRepo;


    @GetMapping
    public List<Order> getOrderInfo(@RequestParam(value = "id", required = false) Integer id) {
        System.out.println("Get All Orders API is called");
        return orderRepo.getAllOrders();
    }
    @GetMapping("/{id}")
    public Order getOrderByID(@PathVariable Integer id) {
        System.out.println("Get Order By ID API is called");
        return orderRepo.getOrder(id);
    }

    @PostMapping
    public String postOrderInfo(@RequestBody Order order) {
        // check customer present with the given ID
        Customer customer = customerRepo.getCustomer(order.getCustomerId());
        if (customer == null) {
            return "Customer with the Given Id not present";
        }
        // check product present with the given id and stock is enough to place the order
        Product product = productRepo.getProduct(order.getProductId());
        if (product == null) {
            return "Product with the Given Id not present";
        } else if (product.getStock() < order.getQuantity()) {
            return "Stock is not enough to place the order";
        }

        order.setOrderValue(order.getQuantity() * product.getPrice());
        int rowsAffected = orderRepo.addOrder(order);
        return (rowsAffected > 0) ? "Order Added Successfully" : "Order Not Added";
    }

}
