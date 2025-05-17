package com.alignease.v1.repository;

import com.alignease.v1.entity.ProductBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBookingRepository extends JpaRepository<ProductBooking, Long> {
}
