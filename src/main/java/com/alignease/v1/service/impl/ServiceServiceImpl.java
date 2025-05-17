package com.alignease.v1.service.impl;

import com.alignease.v1.dto.request.ServiceBookingRequest;
import com.alignease.v1.dto.request.ServiceRequest;
import com.alignease.v1.dto.response.ServiceResponse;
import com.alignease.v1.entity.*;
import com.alignease.v1.exception.AlignEaseValidationsException;
import com.alignease.v1.repository.ServiceRepository;
import com.alignease.v1.repository.UserRepository;
import com.alignease.v1.service.FileStorageService;
import com.alignease.v1.service.ServiceService;
import com.alignease.v1.utils.Messages;
import com.alignease.v1.utils.RequestStatus;
import com.alignease.v1.utils.ResponseCodes;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceServiceImpl.class);

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Messages messages;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    @Transactional
    public ServiceResponse addService(ServiceRequest serviceRequest) {
        logger.info("Add Service Starts");

        ServiceResponse response = new ServiceResponse();

        try {
            if (serviceRequest.getImageFile() != null) {
                validateImage(serviceRequest.getImageFile());
            }

            Service service = modelMapper.map(serviceRequest, Service.class);

            if (serviceRequest.getImageFile() != null && !serviceRequest.getImageFile().isEmpty()) {
                String fileName = fileStorageService.storeFile(serviceRequest.getImageFile());
                service.setImageName(fileName);
                service.setImagePath("/service-images/" + fileName);
            }

            Service savedService = serviceRepository.save(service);

            response.setService(savedService);
            response.setStatus(RequestStatus.SUCCESS.getStatus());
            response.setResponseCode(ResponseCodes.SUCCESS);
            response.setMessage(messages.getMessageForResponseCode(ResponseCodes.SERVICE_ADD_SUCCESS, null));

            logger.info("Add Service Success");
        } catch (Exception e) {
            logger.error("Error adding service: {}", e.getMessage(), e);
            throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE,
                    messages.getMessageForResponseCode(ResponseCodes.SERVICE_ADD_FAILURE, null));
        }

        return response;
    }

    private void validateImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new AlignEaseValidationsException("Image file is empty");
        }

        String contentType = file.getContentType();
        if (!Arrays.asList("image/jpeg", "image/png", "image/gif").contains(contentType)) {
            throw new AlignEaseValidationsException("Only JPG, PNG or GIF images are allowed");
        }

        if (file.getSize() > 10_000_000) {
            throw new AlignEaseValidationsException("File size exceeds 10MB limit");
        }
    }

    @Override
    @Transactional
    public ServiceResponse updateService(Long serviceId, ServiceRequest serviceRequest) {
        logger.info("Update Service Starts for serviceId: {}", serviceId);

        ServiceResponse response = new ServiceResponse();

        try {
            Optional<Service> serviceOpt = serviceRepository.findById(serviceId);

            if (serviceOpt.isEmpty()) {
                logger.error("Service not found with ID: {}", serviceId);
                throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE,
                        messages.getMessageForResponseCode(ResponseCodes.SERVICE_NOT_FOUND, null));
            }

            Service service = serviceOpt.get();
            service.setName(serviceRequest.getName());
            service.setDescription(serviceRequest.getDescription());
            service.setCost(serviceRequest.getCost());
            service.setEstimatedTime(serviceRequest.getEstimatedTime());

            Service updatedService = serviceRepository.save(service);

            response.setService(updatedService);
            response.setStatus(RequestStatus.SUCCESS.getStatus());
            response.setResponseCode(ResponseCodes.SUCCESS);
            response.setMessage(messages.getMessageForResponseCode(ResponseCodes.SERVICE_UPDATE_SUCCESS, null));

            logger.info("Update Service Success");
        } catch (Exception e) {
            logger.error("Error updating service: {}", e.getMessage(), e);
            throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE,
                    messages.getMessageForResponseCode(ResponseCodes.SERVICE_UPDATE_FAILURE, null));
        }

        return response;
    }

    @Override
    public ServiceResponse getServiceById(Long serviceId) {
        logger.info("Get Service by ID Starts for serviceId: {}", serviceId);

        ServiceResponse response = new ServiceResponse();

        try {
            Optional<Service> serviceOpt = serviceRepository.findById(serviceId);

            if (serviceOpt.isEmpty()) {
                logger.error("Service not found with ID: {}", serviceId);
                throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE,
                        messages.getMessageForResponseCode(ResponseCodes.SERVICE_NOT_FOUND, null));
            }

            response.setService(serviceOpt.get());
            response.setStatus(RequestStatus.SUCCESS.getStatus());
            response.setResponseCode(ResponseCodes.SUCCESS);
            response.setMessage(messages.getMessageForResponseCode(ResponseCodes.SERVICE_FETCH_SUCCESS, null));

            logger.info("Get Service by ID Success");
        } catch (Exception e) {
            logger.error("Error fetching service: {}", e.getMessage(), e);
            throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE,
                    messages.getMessageForResponseCode(ResponseCodes.SERVICE_FETCH_FAILURE, null));
        }

        return response;
    }

    @Override
    public ServiceResponse getAllServices() {
        logger.info("Get All Services Starts");

        ServiceResponse response = new ServiceResponse();

        try {
            List<Service> services = serviceRepository.findAll();
            response.setServices(services);
            response.setStatus(RequestStatus.SUCCESS.getStatus());
            response.setResponseCode(ResponseCodes.SUCCESS);
            response.setMessage(messages.getMessageForResponseCode(ResponseCodes.SERVICE_FETCH_SUCCESS, null));

            logger.info("Get All Services Success. Found {} services", services.size());
        } catch (Exception e) {
            logger.error("Error fetching all services: {}", e.getMessage(), e);
            throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE,
                    messages.getMessageForResponseCode(ResponseCodes.SERVICE_FETCH_FAILURE, null));
        }

        return response;
    }

    @Override
    @Transactional
    public ServiceResponse deleteService(Long serviceId) {
        logger.info("Delete Service Starts for serviceId: {}", serviceId);

        ServiceResponse response = new ServiceResponse();

        try {
            Optional<Service> serviceOpt = serviceRepository.findById(serviceId);

            if (serviceOpt.isEmpty()) {
                logger.error("Service not found with ID: {}", serviceId);
                throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE,
                        messages.getMessageForResponseCode(ResponseCodes.SERVICE_NOT_FOUND, null));
            }

            serviceRepository.delete(serviceOpt.get());

            response.setStatus(RequestStatus.SUCCESS.getStatus());
            response.setResponseCode(ResponseCodes.SUCCESS);
            response.setMessage(messages.getMessageForResponseCode(ResponseCodes.SERVICE_DELETE_SUCCESS, null));

            logger.info("Delete Service Success");
        } catch (Exception e) {
            logger.error("Error deleting service: {}", e.getMessage(), e);
            throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE,
                    messages.getMessageForResponseCode(ResponseCodes.SERVICE_DELETE_FAILURE, null));
        }

        return response;
    }

    @Override
    @Transactional
    public ServiceResponse bookService(ServiceBookingRequest request) {
        logger.info("Book Service Starts for serviceId: {}", request.getServiceId());

        ServiceResponse response = new ServiceResponse();

        try {
            Optional<Service> serviceOpt = serviceRepository.findById(request.getServiceId());
            if (serviceOpt.isEmpty()) {
                logger.error("Service not found with ID: {}", request.getServiceId());
                throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE,
                        messages.getMessageForResponseCode(ResponseCodes.SERVICE_NOT_FOUND, null));
            }

            Optional<User> userOpt = userRepository.findById(request.getUserId());
            if (userOpt.isEmpty()) {
                logger.error("User not found with ID: {}", request.getUserId());
                throw new AlignEaseValidationsException(ResponseCodes.BAD_REQUEST_CODE,
                        messages.getMessageForResponseCode(ResponseCodes.USER_NOT_FOUND, null));
            }

            ServiceBooking booking = new ServiceBooking();
            booking.setService(serviceOpt.get());
            booking.setUser(userOpt.get());
            booking.setBookingDate(LocalDateTime.now());
            booking.setBookingStatus(BookingStatus.PENDING);

            serviceOpt.get().getServiceBookings().add(booking);
            Service updatedService = serviceRepository.save(serviceOpt.get());

            response.setService(updatedService);
            response.setStatus(RequestStatus.SUCCESS.getStatus());
            response.setResponseCode(ResponseCodes.SUCCESS);
            response.setMessage(messages.getMessageForResponseCode(
                    ResponseCodes.SERVICE_BOOKING_SUCCESS, null));

            logger.info("Service booking successful for service ID: {}", request.getServiceId());
        } catch (Exception e) {
            logger.error("Error booking service: {}", e.getMessage(), e);
            throw new AlignEaseValidationsException(
                    ResponseCodes.BAD_REQUEST_CODE,
                    messages.getMessageForResponseCode(ResponseCodes.SERVICE_BOOKING_FAILURE, null));
        }

        return response;
    }
}