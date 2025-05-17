package com.alignease.v1.service;

import com.alignease.v1.dto.request.ProductBookingHistoryRequest;
import com.alignease.v1.dto.response.ProductResponse;

public interface UserService {
    ProductResponse getUserBookingHistory(ProductBookingHistoryRequest request);
}
