package com.alignease.v1.service.impl;

import com.alignease.v1.dto.response.ProductResponse;
import com.alignease.v1.dto.response.ServiceResponse;
import com.alignease.v1.entity.*;
import com.alignease.v1.exception.AlignEaseValidationsException;
import com.alignease.v1.repository.ProductBookingRepository;
import com.alignease.v1.repository.ServiceBookingRepository;
import com.alignease.v1.service.AdminService;
import com.alignease.v1.utils.Messages;
import com.alignease.v1.utils.RequestStatus;
import com.alignease.v1.utils.ResponseCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private ProductBookingRepository productBookingRepository;

    @Autowired
    private ServiceBookingRepository serviceBookingRepository;

    @Autowired
    private Messages messages;

    @Override
    @Transactional
    public ProductResponse approveProductBooking(Long bookingId) {
        logger.info("Approving product booking ID: {}", bookingId);
        return updateProductBookingStatus(bookingId, BookingStatus.CONFIRMED);
    }

    @Override
    @Transactional
    public ProductResponse rejectProductBooking(Long bookingId) {
        logger.info("Rejecting product booking ID: {}", bookingId);
        return updateProductBookingStatus(bookingId, BookingStatus.REJECTED);
    }

    @Override
    @Transactional
    public ServiceResponse approveServiceBooking(Long bookingId) {
        logger.info("Approving service booking ID: {}", bookingId);
        return updateServiceBookingStatus(bookingId, BookingStatus.CONFIRMED);
    }

    @Override
    @Transactional
    public ServiceResponse rejectServiceBooking(Long bookingId) {
        logger.info("Rejecting service booking ID: {}", bookingId);
        return updateServiceBookingStatus(bookingId, BookingStatus.REJECTED);
    }

    private ProductResponse updateProductBookingStatus(Long bookingId, BookingStatus status) {
        ProductResponse response = new ProductResponse();

        Optional<ProductBooking> bookingOptional = productBookingRepository.findById(bookingId);

        if (bookingOptional.isEmpty()) {
            logger.error("Product booking not found with ID: {}", bookingId);
            response.setStatus(RequestStatus.FAILURE.getStatus());
            response.setResponseCode(ResponseCodes.PRODUCT_BOOKING_NOT_FOUND);
            response.setMessage(messages.getMessageForResponseCode(ResponseCodes.PRODUCT_BOOKING_NOT_FOUND, null));
            return response;
        }

        ProductBooking booking = bookingOptional.get();

        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            logger.error("Product booking ID {} is not in PENDING state", bookingId);
            response.setStatus(RequestStatus.FAILURE.getStatus());
            response.setResponseCode(ResponseCodes.INVALID_BOOKING_STATE);
            response.setMessage(messages.getMessageForResponseCode(ResponseCodes.INVALID_BOOKING_STATE, null));
            return response;
        }

        booking.setBookingStatus(status);
        productBookingRepository.save(booking);

        if (status == BookingStatus.REJECTED) {
            Product product = booking.getProduct();
            if (product != null && product.getInventory() != null) {
                Inventory inventory = product.getInventory();
                int bookedQuantity = booking.getQuantity() != null ? booking.getQuantity() : 0;
                inventory.setQuantity(inventory.getQuantity() + bookedQuantity);

                if (inventory.getQuantity() > 0) {
                    inventory.setStockStatus(StockStatus.IN_STOCK);
                }

                product.setInventory(inventory);
                productBookingRepository.save(booking);
            } else {
                logger.warn("No inventory found for product booking ID: {}", bookingId);
            }
        }

        response.setStatus(RequestStatus.SUCCESS.getStatus());
        response.setResponseCode(ResponseCodes.SUCCESS);
        response.setMessage(messages.getMessageForResponseCode(
                status == BookingStatus.CONFIRMED ?
                        ResponseCodes.PRODUCT_BOOKING_APPROVED :
                        ResponseCodes.PRODUCT_BOOKING_REJECTED,
                null));

        logger.info("Product booking ID {} status updated to {}", bookingId, status);
        return response;
    }

    private ServiceResponse updateServiceBookingStatus(Long bookingId, BookingStatus status) {
        ServiceResponse response = new ServiceResponse();

        Optional<ServiceBooking> bookingOptional = serviceBookingRepository.findById(bookingId);

        if (bookingOptional.isEmpty()) {
            logger.error("Service booking not found with ID: {}", bookingId);
            response.setStatus(RequestStatus.FAILURE.getStatus());
            response.setResponseCode(ResponseCodes.SERVICE_BOOKING_NOT_FOUND);
            response.setMessage(messages.getMessageForResponseCode(ResponseCodes.SERVICE_BOOKING_NOT_FOUND, null));
            return response;
        }

        ServiceBooking booking = bookingOptional.get();

        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            logger.error("Service booking ID {} is not in PENDING state", bookingId);
            response.setStatus(RequestStatus.FAILURE.getStatus());
            response.setResponseCode(ResponseCodes.INVALID_BOOKING_STATE);
            response.setMessage(messages.getMessageForResponseCode(ResponseCodes.INVALID_BOOKING_STATE, null));
            return response;
        }

        booking.setBookingStatus(status);
        serviceBookingRepository.save(booking);

        response.setStatus(RequestStatus.SUCCESS.getStatus());
        response.setResponseCode(ResponseCodes.SUCCESS);
        response.setMessage(messages.getMessageForResponseCode(
                status == BookingStatus.CONFIRMED ?
                        ResponseCodes.SERVICE_BOOKING_APPROVED :
                        ResponseCodes.SERVICE_BOOKING_REJECTED,
                null));

        logger.info("Service booking ID {} status updated to {}", bookingId, status);
        return response;
    }

    @Override
    public ServiceResponse getAllServiceBookings(BookingStatus bookingStatus) {
        logger.info("Fetching all service bookings with status: {}", bookingStatus);

        ServiceResponse serviceResponse = new ServiceResponse();
        if (bookingStatus != null) {
            List<ServiceBooking> byBookingStatus = serviceBookingRepository.findByBookingStatus(bookingStatus);
            serviceResponse.setServiceBookings(byBookingStatus);
        } else {
            List<ServiceBooking> all = serviceBookingRepository.findAll();
            serviceResponse.setServiceBookings(all);
        }

        serviceResponse.setStatus(RequestStatus.SUCCESS.getStatus());
        serviceResponse.setResponseCode(ResponseCodes.SUCCESS);
        logger.info("Fetching service bookings Ends");
        return serviceResponse;
    }

    @Override
    public ProductResponse getAllProductBookings(BookingStatus bookingStatus) {
        logger.info("Fetching all product bookings with status: {}", bookingStatus);

        ProductResponse productResponse = new ProductResponse();
        if (bookingStatus != null) {
            List<ProductBooking> byBookingStatus = productBookingRepository.findByBookingStatus(bookingStatus);
            productResponse.setProductBookings(byBookingStatus);
        } else {
            List<ProductBooking> all = productBookingRepository.findAll();
            productResponse.setProductBookings(all);
        }

        productResponse.setStatus(RequestStatus.SUCCESS.getStatus());
        productResponse.setResponseCode(ResponseCodes.SUCCESS);
        logger.info("Fetching product bookings Ends");
        return productResponse;
    }
}