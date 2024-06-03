package com.tien.ecommercespringboot.repository;

import com.tien.ecommercespringboot.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {
    boolean existsByEmail(String email);

    boolean existsByName(String name);

    Optional<SellerEntity> findByEmail(String email);
}
