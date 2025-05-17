package com.alignease.v1.service;

import com.alignease.v1.dto.request.ProductBookingHistoryRequest;
import com.alignease.v1.dto.request.ServiceBookingHistoryRequest;
import com.alignease.v1.dto.response.ProductResponse;
import com.alignease.v1.dto.response.ServiceResponse;

public interface UserService {
    ProductResponse getUserProductBookingHistory(ProductBookingHistoryRequest request);
    ServiceResponse getUserServiceBookingHistory(ServiceBookingHistoryRequest request);
}
