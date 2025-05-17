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

        ProductBooking booking = productBookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    logger.error("Product booking not found with ID: {}", bookingId);
                    return new AlignEaseValidationsException(
                            messages.getMessageForResponseCode(ResponseCodes.PRODUCT_BOOKING_NOT_FOUND, null));
                });

        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            logger.error("Product booking ID {} is not in PENDING state", bookingId);
            throw new AlignEaseValidationsException(
                    messages.getMessageForResponseCode(ResponseCodes.INVALID_BOOKING_STATE, null));
        }

        booking.setBookingStatus(status);
        productBookingRepository.save(booking);

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

        ServiceBooking booking = serviceBookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    logger.error("Service booking not found with ID: {}", bookingId);
                    return new AlignEaseValidationsException(
                            messages.getMessageForResponseCode(ResponseCodes.SERVICE_BOOKING_NOT_FOUND, null));
                });

        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            logger.error("Service booking ID {} is not in PENDING state", bookingId);
            throw new AlignEaseValidationsException(
                    messages.getMessageForResponseCode(ResponseCodes.INVALID_BOOKING_STATE, null));
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
}