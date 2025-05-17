package com.alignease.v1.service;

import com.alignease.v1.dto.request.ServiceBookingRequest;
import com.alignease.v1.dto.request.ServiceRequest;
import com.alignease.v1.dto.response.ServiceResponse;

public interface ServiceService {
    ServiceResponse addService(ServiceRequest serviceRequest);
    ServiceResponse updateService(Long serviceId, ServiceRequest serviceRequest);
    ServiceResponse getServiceById(Long serviceId);
    ServiceResponse getAllServices();
    ServiceResponse deleteService(Long serviceId);
    ServiceResponse bookService(ServiceBookingRequest request);
}
