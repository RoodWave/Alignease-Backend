package com.alignease.v1.service.impl;

import com.alignease.v1.dto.request.ProductReviewRequest;
import com.alignease.v1.dto.request.ReviewStatusRequest;
import com.alignease.v1.dto.request.ServiceReviewRequest;
import com.alignease.v1.dto.response.ReviewResponse;
import com.alignease.v1.entity.*;
import com.alignease.v1.exception.AlignEaseValidationsException;
import com.alignease.v1.repository.ProductBookingRepository;
import com.alignease.v1.repository.ReviewRepository;
import com.alignease.v1.repository.ServiceBookingRepository;
import com.alignease.v1.service.ReviewService;
import com.alignease.v1.utils.Messages;
import com.alignease.v1.utils.RequestStatus;
import com.alignease.v1.utils.ResponseCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductBookingRepository productBookingRepository;

    @Autowired
    private ServiceBookingRepository serviceBookingRepository;

    @Autowired
    private Messages messages;

    @Override
    @Transactional
    public ReviewResponse createProductReview(ProductReviewRequest request) {
        logger.info("Creating product review for booking ID: {}", request.getProductBookingId());

        ReviewResponse response = new ReviewResponse();

        try {
            ProductBooking productBooking = productBookingRepository.findById(request.getProductBookingId())
                    .orElseThrow(() -> new AlignEaseValidationsException(
                            messages.getMessageForResponseCode(ResponseCodes.PRODUCT_BOOKING_NOT_FOUND, null)));

            if (productBooking.getReview() != null) {
                throw new AlignEaseValidationsException(
                        messages.getMessageForResponseCode(ResponseCodes.REVIEW_ALREADY_EXISTS, null));
            }

            Review review = new Review();
            review.setTitle(request.getTitle());
            review.setContent(request.getContent());
            review.setReviewStatus(ReviewStatus.PENDING);
            review.setProductBooking(productBooking);

            Review savedReview = reviewRepository.save(review);
            productBooking.setReview(savedReview);
            productBookingRepository.save(productBooking);

            mapReviewToResponse(savedReview, response, false);
            response.setStatus(RequestStatus.SUCCESS.getStatus());
            response.setResponseCode(ResponseCodes.SUCCESS);
            response.setMessage(messages.getMessageForResponseCode(ResponseCodes.PRODUCT_REVIEW_CREATED, null));

            logger.info("Product review created successfully with ID: {}", savedReview.getReviewId());
        } catch (Exception e) {
            logger.error("Error creating product review: {}", e.getMessage());
            throw new AlignEaseValidationsException(
                    messages.getMessageForResponseCode(ResponseCodes.PRODUCT_REVIEW_CREATION_FAILED, null));
        }

        return response;
    }

    @Override
    @Transactional
    public ReviewResponse createServiceReview(ServiceReviewRequest request) {
        logger.info("Creating service review for booking ID: {}", request.getServiceBookingId());

        ReviewResponse response = new ReviewResponse();

        try {
            ServiceBooking serviceBooking = serviceBookingRepository.findById(request.getServiceBookingId())
                    .orElseThrow(() -> new AlignEaseValidationsException(
                            messages.getMessageForResponseCode(ResponseCodes.SERVICE_BOOKING_NOT_FOUND, null)));

            if (serviceBooking.getReview() != null) {
                throw new AlignEaseValidationsException(
                        messages.getMessageForResponseCode(ResponseCodes.REVIEW_ALREADY_EXISTS, null));
            }

            Review review = new Review();
            review.setTitle(request.getTitle());
            review.setContent(request.getContent());
            review.setReviewStatus(ReviewStatus.PENDING);
            review.setServiceBooking(serviceBooking);

            Review savedReview = reviewRepository.save(review);
            serviceBooking.setReview(savedReview);
            serviceBookingRepository.save(serviceBooking);

            mapReviewToResponse(savedReview, response, true);
            response.setStatus(RequestStatus.SUCCESS.getStatus());
            response.setResponseCode(ResponseCodes.SUCCESS);
            response.setMessage(messages.getMessageForResponseCode(ResponseCodes.SERVICE_REVIEW_CREATED, null));

            logger.info("Service review created successfully with ID: {}", savedReview.getReviewId());
        } catch (Exception e) {
            logger.error("Error creating service review: {}", e.getMessage());
            throw new AlignEaseValidationsException(
                    messages.getMessageForResponseCode(ResponseCodes.SERVICE_REVIEW_CREATION_FAILED, null));
        }

        return response;
    }

    @Override
    @Transactional
    public ReviewResponse updateReviewStatus(ReviewStatusRequest request) {
        logger.info("Updating review status for review ID: {}", request.getReviewId());

        ReviewResponse response = new ReviewResponse();

        try {
            Review review = reviewRepository.findById(request.getReviewId())
                    .orElseThrow(() -> new AlignEaseValidationsException(
                            messages.getMessageForResponseCode(ResponseCodes.REVIEW_NOT_FOUND, null)));

            review.setReviewStatus(request.getStatus());
            Review updatedReview = reviewRepository.save(review);

            boolean isServiceReview = updatedReview.getServiceBooking() != null;
            mapReviewToResponse(updatedReview, response, isServiceReview);

            response.setStatus(RequestStatus.SUCCESS.getStatus());
            response.setResponseCode(ResponseCodes.SUCCESS);
            response.setMessage(messages.getMessageForResponseCode(ResponseCodes.REVIEW_STATUS_UPDATED, null));

            logger.info("Review status updated successfully for review ID: {}", request.getReviewId());
        } catch (Exception e) {
            logger.error("Error updating review status: {}", e.getMessage());
            throw new AlignEaseValidationsException(
                    messages.getMessageForResponseCode(ResponseCodes.REVIEW_STATUS_UPDATE_FAILED, null));
        }

        return response;
    }

    private void mapReviewToResponse(Review review, ReviewResponse response, boolean isServiceReview) {
        response.setReviewId(review.getReviewId());
        response.setTitle(review.getTitle());
        response.setContent(review.getContent());
        response.setReviewStatus(review.getReviewStatus());
        response.setServiceBooking(isServiceReview);

        if (isServiceReview) {
            response.setBookingId(review.getServiceBooking().getServiceBookingId());
        } else {
            response.setBookingId(review.getProductBooking().getProductBookingId());
        }
    }
}