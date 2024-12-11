package org.kvn.onlineRetailStore3.repository;

import org.kvn.onlineRetailStore3.entity.Customer;

import java.util.List;

public interface CustomerRepoInterface {
    public int addCustomer(Customer customer);
    public int deleteCustomer(int id);
    public int updateCustomer(int id, String address);
    public List<Customer> getAllCustomers();
    public Customer getCustomer(int id);
}
