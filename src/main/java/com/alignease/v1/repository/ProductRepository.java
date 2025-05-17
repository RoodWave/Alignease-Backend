package com.alignease.v1.repository;

import com.alignease.v1.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductIdAndIsDeleted(Long productId, String isDeleted);

    List<Product> findByIsDeleted(String isDeleted);
}
