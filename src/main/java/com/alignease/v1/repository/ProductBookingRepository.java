package com.alignease.v1.repository;

import com.alignease.v1.entity.BookingStatus;
import com.alignease.v1.entity.ProductBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductBookingRepository extends JpaRepository<ProductBooking, Long> {
    List<ProductBooking> findByBookingStatus(BookingStatus bookingStatus);
}
