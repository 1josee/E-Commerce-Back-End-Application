package com.tien.ecommercespringboot.config;

import com.tien.ecommercespringboot.entity.AdminEntity;
import com.tien.ecommercespringboot.entity.CustomerEntity;
import com.tien.ecommercespringboot.entity.SellerEntity;
import com.tien.ecommercespringboot.repository.AdminRepository;
import com.tien.ecommercespringboot.repository.CustomerRepository;
import com.tien.ecommercespringboot.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final AdminRepository adminRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            CustomerEntity customer = customerRepository.findByEmail(username)
                    .orElse(null);
            if(customer != null){
               return customer;
            }
            SellerEntity seller = sellerRepository.findByEmail(username)
                    .orElse(null);
            if(seller != null){
                return seller;
            }
            return adminRepository.findByEmail(username).orElseThrow(() ->new UsernameNotFoundException("User not found"));
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
