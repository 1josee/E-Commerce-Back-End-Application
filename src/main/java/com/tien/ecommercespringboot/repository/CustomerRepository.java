package com.tien.ecommercespringboot.repository;

import com.tien.ecommercespringboot.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByEmail(String email);

    boolean existsByName(String name);

    Optional<CustomerEntity> findByEmail(String email);
}
