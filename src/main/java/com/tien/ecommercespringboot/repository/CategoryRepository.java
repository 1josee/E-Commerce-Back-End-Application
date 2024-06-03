package com.tien.ecommercespringboot.repository;

import com.tien.ecommercespringboot.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findByName(String categoryName);
}
