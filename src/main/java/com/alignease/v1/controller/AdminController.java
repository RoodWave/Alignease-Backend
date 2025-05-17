package com.alignease.v1.controller;

import com.alignease.v1.dto.response.ProductResponse;
import com.alignease.v1.dto.response.ServiceResponse;
import com.alignease.v1.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/product-bookings/{bookingId}/approve")
    public ProductResponse approveProductBooking(@PathVariable Long bookingId) {
        return adminService.approveProductBooking(bookingId);
    }

    @PostMapping("/product-bookings/{bookingId}/reject")
    public ProductResponse rejectProductBooking(@PathVariable Long bookingId) {
        return adminService.rejectProductBooking(bookingId);
    }

    @PostMapping("/service-bookings/{bookingId}/approve")
    public ServiceResponse approveServiceBooking(@PathVariable Long bookingId) {
        return adminService.approveServiceBooking(bookingId);
    }

    @PostMapping("/service-bookings/{bookingId}/reject")
    public ServiceResponse rejectServiceBooking(@PathVariable Long bookingId) {
        return adminService.rejectServiceBooking(bookingId);
    }
}