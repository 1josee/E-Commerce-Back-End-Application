package com.tien.ecommercespringboot.service;

import com.tien.ecommercespringboot.model.Customer;
import com.tien.ecommercespringboot.response.CustomerResponse;

public interface CustomerService {
    Customer register(Customer customer);

    CustomerResponse getCustomerProfile(Long customerId);
}
