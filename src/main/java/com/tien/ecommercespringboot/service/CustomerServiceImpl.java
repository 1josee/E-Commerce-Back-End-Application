package com.tien.ecommercespringboot.service;

import com.tien.ecommercespringboot.entity.CustomerEntity;
import com.tien.ecommercespringboot.mapper.CustomerMapper;
import com.tien.ecommercespringboot.model.Customer;
import com.tien.ecommercespringboot.repository.CustomerRepository;
import com.tien.ecommercespringboot.response.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    @Override
    public Customer register(Customer customer) {
        if(customerRepository.existsByEmail(customer.getEmail())){
            throw new RuntimeException("The email \"" + customer.getEmail() + "\" is already exists");
        }
        if(customerRepository.existsByName(customer.getName())){
            throw new RuntimeException("The username \"" + customer.getName() + "\" is already exists");
        }
        CustomerEntity customerEntity = customerMapper.toCustomerEntity(customer);
        customerRepository.save(customerEntity);

        return customer;
    }

    @Override
    public CustomerResponse getCustomerProfile(Long customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new UsernameNotFoundException("Customer Not Found"));

        return customerMapper.toCustomerResponse(customerEntity);
    }
}
