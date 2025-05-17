package com.alignease.v1.controller;

import com.alignease.v1.dto.request.ServiceBookingRequest;
import com.alignease.v1.dto.request.ServiceRequest;
import com.alignease.v1.dto.response.ServiceResponse;
import com.alignease.v1.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping("/add")
    public ServiceResponse addService(@RequestBody ServiceRequest request) {
        return serviceService.addService(request);
    }

    @PostMapping("/update/{serviceId}")
    public ServiceResponse updateService(@PathVariable Long serviceId, @RequestBody ServiceRequest request) {
        return serviceService.updateService(serviceId, request);
    }

    @GetMapping("/get/{serviceId}")
    public ServiceResponse getServiceById(@PathVariable Long serviceId) {
        return serviceService.getServiceById(serviceId);
    }

    @GetMapping("/list")
    public ServiceResponse getAllServices() {
        return serviceService.getAllServices();
    }

    @DeleteMapping("/delete/{serviceId}")
    public ServiceResponse deleteService(@PathVariable Long serviceId) {
        return serviceService.deleteService(serviceId);
    }

    @PostMapping("/book")
    public ServiceResponse bookService(@RequestBody ServiceBookingRequest request) {
        return serviceService.bookService(request);
    }
}