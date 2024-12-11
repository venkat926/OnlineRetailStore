package org.kvn.onlineRetailStore3.service;

import jakarta.inject.Inject;
import org.kvn.onlineRetailStore3.entity.Customer;
import org.kvn.onlineRetailStore3.entity.CustomerEntity;
import org.kvn.onlineRetailStore3.repository.CustomerJPARepo;
import org.kvn.onlineRetailStore3.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
//    @Inject
//    private CustomerRepo customerRepo;
//
//    // add customer to DB
//    public int addCustomer(Customer customer) {
//        try {
//            return customerRepo.addCustomer(customer);
//        } catch (DataAccessException dataAccessException) {
//            System.err.println("Error occurred while accessing the database: " + dataAccessException.getMessage());
//            return 0;
//        }
//    }
//
//    // delete customer from DB
//    public int deleteCustomer(int id) {
//        return customerRepo.deleteCustomer(id);
//    }
//
//    // update Customer
//    public int updateCustomer(int id, String address) {
//        return customerRepo.updateCustomer(id, address);
//    }
//
//    // get all customers
//    public List<Customer> getAllCustomers() {
//        return customerRepo.getAllCustomers();
//    }
//
//    // get customer by id
//    public Customer getCustomerByID(int id) {
//        return customerRepo.getCustomer(id);
//    }

    @Autowired
    private CustomerJPARepo customerJPARepo;

    public int addCustomer(Customer customer) {
        CustomerEntity customerEntity = CustomerEntity.builder()
                .name(customer.getName())
                .address(customer.getAddress())
                .phoneNo(customer.getPhoneNo())
                .build();
       CustomerEntity savedEntity = customerJPARepo.save(customerEntity);
       return savedEntity.getId()==null ? 0 : 1;
    }

    // delete customer from DB
    public int deleteCustomer(int id) {
        System.out.println("deleting customer");
        if (customerJPARepo.existsById(id)) {
            customerJPARepo.deleteById(id);
            return 1;
        } else {
            return 0;
        }
    }

    // update Customer
    public int updateCustomer(int id, String address) {
        Optional<CustomerEntity> optionalCustomer = customerJPARepo.findById(id);
        if (optionalCustomer.isPresent()) {
            CustomerEntity customerEntity = optionalCustomer.get();
            customerEntity.setAddress(address);
            customerJPARepo.save(customerEntity);
            return 1;
        } else {
            return 0;
        }
    }

    // get all customers
    public List<Customer> getAllCustomers() {
        List<CustomerEntity> customerEntityList = customerJPARepo.findAll();
        return customerEntityList.stream()
                .map(ce -> Customer.builder().id(ce.getId())
                .name(ce.getName()).address(ce.getAddress()).phoneNo(ce.getPhoneNo()).build())
                .collect(Collectors.toList());
    }

    // get customer by id
    public Customer getCustomerByID(int id) {
        Optional<CustomerEntity> optionalCustomerEntity = customerJPARepo.findById(id);
        if (optionalCustomerEntity.isPresent()) {
            CustomerEntity customerEntity = optionalCustomerEntity.get();
            return Customer.builder()
                    .id(customerEntity.getId())
                    .name(customerEntity.getName())
                    .address(customerEntity.getAddress())
                    .phoneNo(customerEntity.getPhoneNo())
                    .build();
        } else {
            return null;
        }
    }

}
