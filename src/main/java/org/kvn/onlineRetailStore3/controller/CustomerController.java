package org.kvn.onlineRetailStore3.controller;

import org.kvn.onlineRetailStore3.entity.Customer;
import org.kvn.onlineRetailStore3.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
@ResponseBody
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        System.out.println("Get Customer API is called");
        return customerService.getAllCustomers();
    }
    @GetMapping("/{id}")
    public Customer getCustomerByID(@PathVariable Integer id) {
        System.out.println("Get Customer By ID API is called");
        return customerService.getCustomerByID(id);
    }

    @PostMapping
    public String postCustomerInfo(@RequestBody Customer customer) {
        int rowsAffected = customerService.addCustomer(customer);
        return (rowsAffected > 0) ? "Customer Added Successfully" : "Customer Not Added";
    }

    @PutMapping
    public String putCustomerInfo(@RequestBody Customer customer) {
        int rowsAffected = customerService.updateCustomer(customer.getId(), customer.getAddress());
        return (rowsAffected > 0) ? "Customer Address Updated Successfully" : "Customer with id: " + customer.getId() + " not found";
    }

    @DeleteMapping
    public String deleteCustomerInfo(@RequestBody Customer customer) {
        int rowsAffected = customerService.deleteCustomer(customer.getId());
        return (rowsAffected > 0) ? "Customer Deleted Successfully" : "Customer with id: " + customer.getId() + " not found";
    }

}
