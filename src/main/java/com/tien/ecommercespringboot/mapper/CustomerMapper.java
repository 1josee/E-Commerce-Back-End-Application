package com.tien.ecommercespringboot.mapper;

import com.tien.ecommercespringboot.entity.CustomerEntity;
import com.tien.ecommercespringboot.entity.Role;
import com.tien.ecommercespringboot.model.Customer;
import com.tien.ecommercespringboot.response.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CustomerMapper {
    private final PasswordEncoder passwordEncoder;
    public CustomerEntity toCustomerEntity(Customer customer){
        String encodePassword = passwordEncoder.encode(customer.getPassword());
        CustomerEntity customerEntity = CustomerEntity.builder()
                .email(customer.getEmail())
                .password(encodePassword)
                .name(customer.getName())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .role(Role.ROLE_CUSTOMER)
                .timeStamp(new Date().toString())
                .build();


        return customerEntity;
    }
//userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
    public CustomerResponse toCustomerResponse(CustomerEntity customerEntity){
        CustomerResponse customer = CustomerResponse.builder()
                .id(customerEntity.getId())
                .email(customerEntity.getEmail())
                .name(customerEntity.getName())
                .phone(customerEntity.getPhone())
                .address(customerEntity.getAddress())
                .build();

        return customer;

    }


}
