package com.alignease.v1.repository;

import com.alignease.v1.entity.BookingStatus;
import com.alignease.v1.entity.ServiceBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceBookingRepository extends JpaRepository<ServiceBooking, Long> {
    List<ServiceBooking> findByBookingStatus(BookingStatus bookingStatus);
    @Query("SELECT sb FROM ServiceBooking sb LEFT JOIN FETCH sb.service WHERE sb.bookingStatus = :bookingStatus")
    List<ServiceBooking> findByBookingStatusWithService(@Param("bookingStatus") BookingStatus bookingStatus);

    @Query("SELECT sb FROM ServiceBooking sb LEFT JOIN FETCH sb.service")
    List<ServiceBooking> findAllWithService();

}
