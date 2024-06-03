package com.tien.ecommercespringboot.repository;

import com.tien.ecommercespringboot.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
