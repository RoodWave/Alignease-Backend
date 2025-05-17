package com.alignease.v1.service.impl;

import com.alignease.v1.dto.request.ProductBookingHistoryRequest;
import com.alignease.v1.dto.request.ServiceBookingHistoryRequest;
import com.alignease.v1.dto.response.ProductResponse;
import com.alignease.v1.dto.response.ServiceResponse;
import com.alignease.v1.entity.ProductBooking;
import com.alignease.v1.entity.ServiceBooking;
import com.alignease.v1.entity.User;
import com.alignease.v1.exception.AlignEaseValidationsException;
import com.alignease.v1.repository.UserRepository;
import com.alignease.v1.service.UserService;
import com.alignease.v1.utils.Messages;
import com.alignease.v1.utils.RequestStatus;
import com.alignease.v1.utils.ResponseCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Messages messages;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public ProductResponse getUserProductBookingHistory(ProductBookingHistoryRequest request) {
        logger.info("Fetching booking history for user ID: {}", request.getUserId());

        ProductResponse productResponse = new ProductResponse();

        Optional<User> userOpt = userRepository.findById(request.getUserId());
        if (userOpt.isEmpty()) {
            logger.error("User not found with ID: {}", request.getUserId());
            throw new AlignEaseValidationsException(
                    messages.getMessageForResponseCode(ResponseCodes.USER_NOT_FOUND, null));
        }

        User user = userOpt.get();
        List<ProductBooking> bookings;

        if (request.getBookingStatus() != null) {
            logger.debug("Filtering bookings by status: {}", request.getBookingStatus());
            bookings = user.getProductBookings().stream()
                    .filter(booking -> booking.getBookingStatus() == request.getBookingStatus())
                    .collect(Collectors.toList());
        } else {
            logger.debug("Fetching all bookings without status filter");
            bookings = user.getProductBookings();
        }

        productResponse.setProductBookings(bookings);
        productResponse.setStatus(RequestStatus.SUCCESS.getStatus());
        productResponse.setResponseCode(ResponseCodes.SUCCESS);
        productResponse.setMessage(messages.getMessageForResponseCode(
                ResponseCodes.PRODUCT_BOOKING_HISTORY_FETCH_SUCCESS, null));

        logger.info("Successfully fetched {} bookings for user ID: {}", bookings.size(), request.getUserId());
        return productResponse;
    }

    @Override
    public ServiceResponse getUserServiceBookingHistory(ServiceBookingHistoryRequest request) {
        logger.info("Fetching service booking history for user ID: {}", request.getUserId());

        ServiceResponse serviceResponse = new ServiceResponse();

        Optional<User> userOpt = userRepository.findById(request.getUserId());
        if (userOpt.isEmpty()) {
            logger.error("User not found with ID: {}", request.getUserId());
            throw new AlignEaseValidationsException(
                    messages.getMessageForResponseCode(ResponseCodes.USER_NOT_FOUND, null));
        }

        User user = userOpt.get();
        List<ServiceBooking> bookings;

        if (request.getBookingStatus() != null) {
            logger.debug("Filtering service bookings by status: {}", request.getBookingStatus());
            bookings = user.getServiceBookings().stream()
                    .filter(booking -> booking.getBookingStatus() == request.getBookingStatus())
                    .collect(Collectors.toList());
        } else {
            logger.debug("Fetching all service bookings without status filter");
            bookings = user.getServiceBookings();
        }

        serviceResponse.setServiceBookings(bookings);
        serviceResponse.setStatus(RequestStatus.SUCCESS.getStatus());
        serviceResponse.setResponseCode(ResponseCodes.SUCCESS);
        serviceResponse.setMessage(messages.getMessageForResponseCode(
                ResponseCodes.SERVICE_BOOKING_HISTORY_FETCH_SUCCESS, null));

        logger.info("Successfully fetched {} service bookings for user ID: {}",
                bookings.size(), request.getUserId());
        return serviceResponse;
    }
}