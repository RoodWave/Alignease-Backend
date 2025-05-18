package com.alignease.v1.service;

import com.alignease.v1.dto.request.ProductReviewRequest;
import com.alignease.v1.dto.request.ReviewStatusRequest;
import com.alignease.v1.dto.request.ServiceReviewRequest;
import com.alignease.v1.dto.response.ReviewFilterResponse;
import com.alignease.v1.dto.response.ReviewResponse;
import com.alignease.v1.entity.ReviewStatus;

public interface ReviewService {
    ReviewResponse createProductReview(ProductReviewRequest request);
    ReviewResponse createServiceReview(ServiceReviewRequest request);
    ReviewResponse updateReviewStatus(ReviewStatusRequest request);
    ReviewFilterResponse fetchReviews(ReviewStatus reviewStatus);
}