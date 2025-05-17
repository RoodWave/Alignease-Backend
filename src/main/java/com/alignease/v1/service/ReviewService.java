package com.alignease.v1.service;

import com.alignease.v1.dto.request.ProductReviewRequest;
import com.alignease.v1.dto.request.ReviewStatusRequest;
import com.alignease.v1.dto.request.ServiceReviewRequest;
import com.alignease.v1.dto.response.ReviewResponse;

public interface ReviewService {
    ReviewResponse createProductReview(ProductReviewRequest request);
    ReviewResponse createServiceReview(ServiceReviewRequest request);
    ReviewResponse updateReviewStatus(ReviewStatusRequest request);
}