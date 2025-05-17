package com.alignease.v1.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductReviewRequest {
    private String title;
    private String content;
    private Long productBookingId;
}
