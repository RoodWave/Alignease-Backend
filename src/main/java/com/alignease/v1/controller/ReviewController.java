package com.alignease.v1.controller;

import com.alignease.v1.dto.request.ProductReviewRequest;
import com.alignease.v1.dto.request.ReviewStatusRequest;
import com.alignease.v1.dto.request.ServiceReviewRequest;
import com.alignease.v1.dto.response.ReviewFilterResponse;
import com.alignease.v1.dto.response.ReviewResponse;
import com.alignease.v1.entity.ReviewStatus;
import com.alignease.v1.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/product")
    public ReviewResponse createProductReview(@RequestBody ProductReviewRequest request) {
        return reviewService.createProductReview(request);
    }

    @PostMapping("/service")
    public ReviewResponse createServiceReview(@RequestBody ServiceReviewRequest request) {
        return reviewService.createServiceReview(request);
    }

    @PutMapping("/status")
    public ReviewResponse updateReviewStatus(@RequestBody ReviewStatusRequest request) {
        return reviewService.updateReviewStatus(request);
    }

    @GetMapping("/list")
    public ReviewFilterResponse fetchReviews(@RequestParam(required = false) ReviewStatus status) {
        return reviewService.fetchReviews(status);
    }
}