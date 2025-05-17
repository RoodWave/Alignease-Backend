package com.alignease.v1.controller;

import com.alignease.v1.dto.request.ServiceBookingRequest;
import com.alignease.v1.dto.request.ServiceRequest;
import com.alignease.v1.dto.response.ServiceResponse;
import com.alignease.v1.exception.AlignEaseValidationsException;
import com.alignease.v1.service.ServiceService;
import com.alignease.v1.utils.ResponseCodes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ServiceResponse addService(
            @RequestPart("service") String serviceJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServiceRequest serviceRequest = objectMapper.readValue(serviceJson, ServiceRequest.class);
            serviceRequest.setImageFile(imageFile);
            return serviceService.addService(serviceRequest);
        } catch (JsonProcessingException e) {
            throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE,
                    "Invalid service request format");
        }
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