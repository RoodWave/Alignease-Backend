package com.alignease.v1.service;

import com.alignease.v1.dto.response.ProductResponse;
import com.alignease.v1.dto.response.ServiceResponse;

public interface AdminService {
    ProductResponse approveProductBooking(Long bookingId);
    ProductResponse rejectProductBooking(Long bookingId);
    ServiceResponse approveServiceBooking(Long bookingId);
    ServiceResponse rejectServiceBooking(Long bookingId);
}
