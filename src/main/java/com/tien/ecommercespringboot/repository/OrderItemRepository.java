package com.tien.ecommercespringboot.repository;

import com.tien.ecommercespringboot.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}
