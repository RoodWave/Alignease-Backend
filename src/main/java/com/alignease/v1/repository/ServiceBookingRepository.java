package com.alignease.v1.repository;

import com.alignease.v1.entity.BookingStatus;
import com.alignease.v1.entity.ServiceBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceBookingRepository extends JpaRepository<ServiceBooking, Long> {
    List<ServiceBooking> findByBookingStatus(BookingStatus bookingStatus);

}
