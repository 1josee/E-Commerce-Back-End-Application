package com.tien.ecommercespringboot.service;

import com.tien.ecommercespringboot.config.JwtService;
import com.tien.ecommercespringboot.entity.AdminEntity;
import com.tien.ecommercespringboot.entity.CustomerEntity;
import com.tien.ecommercespringboot.entity.Role;
import com.tien.ecommercespringboot.entity.SellerEntity;
import com.tien.ecommercespringboot.mapper.CustomerMapper;
import com.tien.ecommercespringboot.mapper.SellerMapper;
import com.tien.ecommercespringboot.model.Admin;
import com.tien.ecommercespringboot.model.Customer;
import com.tien.ecommercespringboot.model.Seller;
import com.tien.ecommercespringboot.repository.AdminRepository;
import com.tien.ecommercespringboot.repository.CustomerRepository;
import com.tien.ecommercespringboot.repository.SellerRepository;
import com.tien.ecommercespringboot.request.AuthenticationRequest;
import com.tien.ecommercespringboot.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;

    @Override
    public AuthenticationResponse cusomterRegister(Customer customer) {
        if(customerRepository.existsByEmail(customer.getEmail())){
            throw new RuntimeException("The email \"" + customer.getEmail() + "\" is already exists");
        }
        if(customerRepository.existsByName(customer.getName())){
            throw new RuntimeException("The username \"" + customer.getName() + "\" is already exists");
        }
        CustomerEntity customerEntity = customerMapper.toCustomerEntity(customer);
        customerRepository.save(customerEntity);
        var jwtToken = jwtService.generateToken(customerEntity);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .email(customer.getEmail())
                .role(String.valueOf(customerEntity.getRole()))
                .build();
    }

    @Override
    public AuthenticationResponse sellerRegister(Seller seller) {
        if (sellerRepository.existsByEmail(seller.getEmail())){
            throw new RuntimeException("The email \"" + seller.getEmail() + "\" is already exists");
        }
        if (sellerRepository.existsByName(seller.getName())){
            throw new RuntimeException("The name \"" + seller.getName() + "\" is already exists");
        }
        SellerEntity sellerEntity = sellerMapper.toSellerEntity(seller);
        sellerRepository.save(sellerEntity);

        var jwtToken = jwtService.generateToken(sellerEntity);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .email(seller.getEmail())
                .role(String.valueOf(sellerEntity.getRole()))
                .build();
    }

    @Override
    public AuthenticationResponse adminRegister(Admin admin) {
        String encodePassword = passwordEncoder.encode(admin.getPassword());
        AdminEntity adminEntity = AdminEntity.builder()
                .email(admin.getEmail())
                .password(encodePassword)
                .role(Role.ROLE_ADMIN)
                .build();
        adminRepository.save(adminEntity);
        var jwtToken = jwtService.generateToken(adminEntity);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        CustomerEntity customerEntity = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var jwtToken = jwtService.generateToken(customerEntity);
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .email(request.getEmail())
                .role(customerEntity.getRole().name())
                .build();
    }




}
