package com.tien.ecommercespringboot.service;

import com.tien.ecommercespringboot.model.Admin;
import com.tien.ecommercespringboot.model.Customer;
import com.tien.ecommercespringboot.model.Seller;
import com.tien.ecommercespringboot.request.AuthenticationRequest;
import com.tien.ecommercespringboot.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse cusomterRegister(Customer customer);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse sellerRegister(Seller seller);

    AuthenticationResponse adminRegister(Admin admin);
}
